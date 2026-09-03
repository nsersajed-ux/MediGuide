package com.example.mediguide

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

data class MedicineAlarm(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val hour: Int,
    val minute: Int,
    val period: String, // "ص" أو "م"
    val recurrence: String = "يوميًا",
    var isEnabled: Boolean = true
)

// مستودع عام لحفظ المنبهات حتى لا تختفي عند التنقل بين الشاشات
object AlarmRepository {
    val alarms = mutableStateListOf(
        MedicineAlarm(title = "دواء الصباح", hour = 8, minute = 0, period = "ص"),
        MedicineAlarm(title = "بعد الغداء", hour = 2, minute = 30, period = "م")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineAlarmScreen() {
    val context = LocalContext.current
    val alarmsList = AlarmRepository.alarms

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⏰", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("منبّه الأدوية", fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "إضافة منبّه", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (alarmsList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "لا توجد منبّهات مضافة حالياً. اضغط على زر الإضافة للبدء.",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(alarmsList, key = { it.id }) { alarm ->
                        AlarmItemCard(
                            alarm = alarm,
                            onToggleChange = { newState ->
                                val index = alarmsList.indexOfFirst { it.id == alarm.id }
                                if (index != -1) {alarmsList[index] = alarmsList[index].copy(isEnabled = newState)
                                    if (newState) {
                                        scheduleAlarm(context, alarmsList[index])
                                    } else {
                                        cancelAlarm(context, alarmsList[index])
                                    }
                                }
                            },
                            onDelete = {
                                cancelAlarm(context, alarm)
                                alarmsList.remove(alarm)
                            }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddAlarmDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { newAlarm ->
                    alarmsList.add(newAlarm)
                    if (newAlarm.isEnabled) {
                        scheduleAlarm(context, newAlarm)
                    }
                    showAddDialog = false
                }
            )
        }
    }
}

// دالة لجدولة المنبه عبر AlarmManager
@SuppressLint("ScheduleExactAlarm")
fun scheduleAlarm(context: Context, alarm: MedicineAlarm) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("MEDICINE_NAME", alarm.title)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarm.id.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        var targetHour = alarm.hour
        if (alarm.period == "م" && targetHour < 12) targetHour += 12
        if (alarm.period == "ص" && targetHour == 12) targetHour = 0

        set(Calendar.HOUR_OF_DAY, targetHour)
        set(Calendar.MINUTE, alarm.minute)
        set(Calendar.SECOND, 0)

        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    try {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    } catch (e: SecurityException) {
        // تجاهل في حال عدم وجود الصلاحية
    }
}

fun cancelAlarm(context: Context, alarm: MedicineAlarm) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarm.id.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}

@Composable
fun AlarmItemCard(
    alarm: MedicineAlarm,
    onToggleChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val formattedTime = String.format(Locale.getDefault(), "%d:%02d", alarm.hour, alarm.minute)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (alarm.isEnabled)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {Row(verticalAlignment = Alignment.CenterVertically) {
                Text("💊", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = alarm.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if (alarm.isEnabled) MaterialTheme.colorScheme.onSurface else Color.Gray
                )
            }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$formattedTime ${alarm.period}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = if (alarm.isEnabled) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = alarm.recurrence,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color.Red.copy(alpha = 0.7f))
                }
                Switch(
                    checked = alarm.isEnabled,
                    onCheckedChange = onToggleChange
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmDialog(
    onDismiss: () -> Unit,
    onAdd: (MedicineAlarm) -> Unit
) {
    var title by remember { mutableStateOf("") }
    val timePickerState = rememberTimePickerState(
        initialHour = 8,
        initialMinute = 0,
        is24Hour = false
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("إضافة منبّه دواء جديد", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("اسم الدواء / المنبّه") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                TimePicker(state = timePickerState)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val rawHour = timePickerState.hour
                        val minute = timePickerState.minute

                        val period = if (rawHour >= 12) "م" else "ص"
                        var hour12 = rawHour % 12
                        if (hour12 == 0) hour12 = 12

                        onAdd(
                            MedicineAlarm(
                                title = title,
                                hour = hour12,
                                minute = minute,
                                period = period
                            )
                        )
                    }
                }
            ) {
                Text("تم")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}