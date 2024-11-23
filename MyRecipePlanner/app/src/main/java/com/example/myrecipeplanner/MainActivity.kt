package com.example.myrecipeplanner

import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecipeplanner.ui.theme.MyRecipePlannerTheme
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyRecipePlannerTheme {
                CalendarApp()
            }
        }
    }
}

@Composable
fun CalendarApp() {
    // 시간에 대한 정보
    val calendarInstance = Calendar.getInstance() // 현재 날짜와 시간으로 초기화된 Calendar 클래스의 새 인스턴스를 생성
    // Calendar 개체가 변경되면(예: 나중에 날짜를 변경) 해당 변경 사항을 반영하도록 UI가 자동으로 재구성
    // by를 쓰면 상태에 대한 읽기 전용 참조를 제공하는 속성 위임이 생성
    // 상태를 직접 업데이트할 수는 없음.
    val date = remember { mutableStateOf(calendarInstance) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarHeader(date)
        CalendarHeaderBtn(date)
        CalendarDayName()
        CalendarDayList(date)
    }

}

@Composable
fun CalendarHeader(date: MutableState<Calendar>) {

    // SimpleDateFormat: Calendar 객체에 포함된 날짜를 문자열로 형식화.
    // date.time: Calendar에서 실제 Date 개체를 검색한 다음 문자열로 형식화.
    val resultTime = SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(date.value.time)

    Text(
        text = resultTime,
        fontSize = 30.sp
    )
}

@Composable
fun CalendarHeaderBtn(date: MutableState<Calendar>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                val newDate = Calendar.getInstance()
                newDate.time = date.value.time
                newDate.add(Calendar.MONTH, -1)
                date.value = newDate
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(
                text = "<",
                color = Color.Green
            )
        }

        Button(
            onClick = {
                val newDate = Calendar.getInstance()
                newDate.time = date.value.time
                newDate.add(Calendar.MONTH, 1)
                date.value = newDate
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(
                text = ">",
                color = Color.Green
            )
        }
    }
}

@Composable
fun CalendarDayName() {
    val nameList = listOf("일", "월", "화", "수", "목", "금", "토")

    Row {
        nameList.forEach {
            Box(
                // weight는 전체 비율에서 얼마만큼 차지할 지 여부
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

@Composable
fun CalendarDayList(date: MutableState<Calendar>) {

    // 달력 그리는 공식 -> Jetpack Compose로 달력 모양 그리는지에 집중!
    date.value.set(Calendar.DAY_OF_MONTH, 1)

    val monthDayMax = date.value.getActualMaximum(Calendar.DAY_OF_MONTH) // 현재 달의 마지막 날
    val monthFirstDay = date.value.get(Calendar.DAY_OF_WEEK) - 1 // 1일이 무슨 요일부터인지 (일 ~ 토 : 0 ~ 6에 대응)
    val monthWeekCount = (monthDayMax + monthFirstDay + 6) / 7 // 현재 달의 week 갯수

    Column {
        repeat(monthWeekCount) { week ->
            Row {
                repeat(7) { day ->
                    // 날짜 구하는 공식
                    val resultDay = week * 7 + day - monthFirstDay + 1

                    if (resultDay in 1..monthDayMax) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "$resultDay")
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyRecipePlannerTheme {
    }
}