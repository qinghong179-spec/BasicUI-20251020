package tw.edu.pu.csim.tcyang.basicui


import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tw.edu.pu.csim.tcyang.basicui.ui.theme.BasicUITheme
// 假設 R.drawable.animal0 到 R.drawable.animal9 已經存在
// 假設 R.raw.tcyang 和 R.raw.fly 已經存在
// 假設 R.string.app_title 和 R.string.app_author 已經存在

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {

    var Animals = listOf(R.drawable.animal0, R.drawable.animal1,
        R.drawable.animal2, R.drawable.animal3,
        R.drawable.animal4, R.drawable.animal5,
        R.drawable.animal6, R.drawable.animal7,
        R.drawable.animal8, R.drawable.animal9)

    var AnimalsName = arrayListOf("鴨子","企鵝", // 企鵝 index = 1
        "青蛙","貓頭鷹","海豚", "牛", "無尾熊", "獅子", "狐狸", "小雞") // 青蛙 index = 2

    // 原始程式碼中的 flag 變數已經移除，因為它只與被替換的按鈕有關。
    // var flag by remember() { mutableStateOf("test") }

    // 取得當前的 Context
    val context = LocalContext.current

    var mper: MediaPlayer? by remember { mutableStateOf(null) }

// 使用 DisposableEffect 來管理 MediaPlayer 的生命週期
    DisposableEffect(Unit) {
        onDispose {
            mper?.release()
            mper = null
        }
    }

    // **********************************************
    // *** 新增：右下角圖片按鈕的狀態宣告 ***
    // **********************************************
    var currentAnimalIndex by remember { mutableStateOf(2) } // 初始為青蛙 (index=2)
    val frogIndex = 2
    val penguinIndex = 1
    // **********************************************

    // *** 改變：使用 Box 作為最外層佈局，以便將按鈕浮動到右下角 ***
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // *** 1. 原始所有的內容放在一個 Column 中 ***
        Column (
            modifier = Modifier
                .fillMaxSize() // 確保 Column 仍然填滿空間
                .background(Color(0xFFE0BBE4)) // 設定背景為淺紫色
                .padding(bottom = 100.dp), // 留出底部空間給浮動按鈕
            horizontalAlignment = Alignment.CenterHorizontally, // 設定水平置中
            verticalArrangement = Arrangement.Top // 設定垂直靠上
        ) {
            Text(text = stringResource(R.string.app_title),
                fontSize = 25.sp,
                color = Color.Blue,
                fontFamily = FontFamily(Font(R.font.kai))
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(text = stringResource(R.string.app_author),
                fontSize = 20.sp,
                color = Color(0xFF654321)
            )

            Spacer(modifier = Modifier.size(10.dp))

            Row {
                Image(
                    painter = painterResource(id = R.drawable.android),
                    contentDescription = "Android 圖示",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Yellow),
                    alpha = 0.6f,
                )

                Image(
                    painter = painterResource(id = R.drawable.compose),
                    contentDescription = "Compose icon",
                    modifier = Modifier.size(100.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.firebase),
                    contentDescription = "Firebase icon",
                    modifier = Modifier.size(100.dp)
                )

            }

            Spacer(modifier = Modifier.size(10.dp))

            LazyRow {
                items(51) { index ->
                    Text(text = "$index:")
                    Text(text = AnimalsName[index % 10])

                    Image(
                        painter = painterResource(id = Animals[index % 10]),
                        contentDescription = "可愛動物",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.size(10.dp))

            // 原來的 '按鈕測試' 和 'flag' 文字已移除，以保持位置和簡潔性。

            // 由於原來的 '按鈕測試' 後面有一個 Spacer(10.dp)，為了保持原有的空間，我們保留這個 Spacer
            Spacer(modifier = Modifier.size(10.dp))

            // 以下是您原有的三個功能按鈕 Row，它們的位置和大小保持不變。
            Row{
                Button(onClick = {
                    mper?.release()  //釋放資源
                    mper = null // 清除舊引用

                    mper = MediaPlayer.create(context, R.raw.tcyang) //設定音樂
                    mper?.start()
                },
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .fillMaxHeight(0.8f),

                    colors = buttonColors(Color.Green)
                ) {
                    Text(text = "歡迎", color = Color.Blue)
                    Text(text = "修課", color = Color.Red)
                    Image(
                        painterResource(id = R.drawable.teacher),
                        contentDescription ="teacher icon")
                }

                Spacer(modifier = Modifier.size(10.dp))

                Button(onClick = {
                    mper?.release()  //釋放資源
                    mper = null // 清除舊引用

                    mper = MediaPlayer.create(context, R.raw.fly) //設定音樂
                    mper?.start()
                },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.4f),

                    colors = buttonColors(Color.Blue)
                ) {
                    Text(text = "展翅飛翔", color = Color.White)
                    Image(
                        painterResource(id = R.drawable.fly),
                        contentDescription ="fly icon")
                }

                Spacer(modifier = Modifier.size(10.dp))

                Button(onClick = {
                    val activity = context as? Activity
                    activity?.finish()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
                    shape = CutCornerShape(10),
                    border = BorderStroke(1.dp, Color.Blue),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                ) {
                    Text(text = "結束App")
                }
            }
        } // Column 結束


        // *** 2. 新增：圖片切換按鈕，使用 Box 的 alignment 定位到右下角 ***
        Button(
            onClick = {
                // 點擊時，切換狀態
                if (currentAnimalIndex == frogIndex) {
                    currentAnimalIndex = penguinIndex
                } else {
                    currentAnimalIndex = frogIndex
                }
                // 顯示 Toast 訊息
                Toast.makeText(context, "已切換為 ${AnimalsName[currentAnimalIndex]}", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd) // *** 關鍵：定位到 Box 的右下角 ***
                .padding(20.dp), // 距離邊緣的間隔

            // 設置透明顏色和 0 邊緣/陰影，讓它看起來像純圖片
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            // 根據 currentAnimalIndex 狀態，自動顯示對應圖片
            Image(
                painter = painterResource(id = Animals[currentAnimalIndex]),
                contentDescription = AnimalsName[currentAnimalIndex] + "圖片按鈕",
                modifier = Modifier.size(100.dp) // 設定圖片大小
            )
        }
    } // Box 結束
}