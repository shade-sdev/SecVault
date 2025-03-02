package ui.components.secvault.passwordinfo

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.models.PasswordCategory
import ui.theme.Font
import ui.theme.PasswordColors
import viewmodel.SecVaultScreenModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PasswordMisc(screenModel: SecVaultScreenModel) {
    val selectedCredential by screenModel.selectedCredential.collectAsState()
    val selectedMenu by screenModel.selectedMenuItem.collectAsState()

    val categories by remember(selectedCredential, selectedMenu) {
        mutableStateOf(screenModel.selectedCredential.value.password?.passwordCategory?.split(",")
                               ?.map(PasswordCategory::valueOf)
                               ?.map(PasswordCategory::value)
                               ?.toList() ?: emptyList())
    }

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

        Row(modifier = Modifier.weight(2f).fillMaxHeight().fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)) {

                Row() {
                    Text(
                        text = "Category", fontSize = 12.sp,
                        fontFamily = Font.RobotoRegular,
                        color = PasswordColors.outline
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3), // Single row, behaving like LazyRow
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { item ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                        .background(Color(0xFF2B2B2B), shape = RoundedCornerShape(2.dp))
                                        .widthIn(min = 56.dp, max = 120.dp)
                                        .height(28.dp)
                            ) {
                                Text(
                                    text = item,
                                    fontSize = 12.sp,
                                    fontFamily = Font.RobotoRegular,
                                    color = Color(0xFFcd9b5b),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                }
            }
        }

        Row(
            modifier = Modifier.weight(4f),
            horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Column() {
                PasswordMiscProgressIndicator()
            }

            Column() {
                Row() {
                    Text(
                        text = "Password Score: 82%",
                        fontSize = 14.sp,
                        fontFamily = Font.RussoOne,
                        color = Color.White,
                    )
                }

                Row() {
                    Column {
                        Text(
                            text = "Password Strength:",
                            fontSize = 12.sp,
                            fontFamily = Font.RobotoRegular,
                            color = Color.Gray,
                        )
                    }

                    Column {
                        Text(
                            text = "Good",
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne,
                            color = Color(0xFF9AE6b4),
                        )
                    }
                }
            }

        }

        Row(modifier = Modifier.weight(3f)) {
            Column() {

                Row() {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            (-4).dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {

                        Row() {
                            Text(
                                text = "Last Modified",
                                fontSize = 10.sp,
                                fontFamily = Font.RussoOne,
                                color = Color(0xFF6C6C6C),
                            )
                        }

                        Row() {
                            Text(
                                text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                fontSize = 12.sp,
                                fontFamily = Font.RussoOne,
                                color = Color.Gray,
                            )
                        }
                    }
                }

                Row() {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            (-4).dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {

                        Row() {
                            Text(
                                text = "Created",
                                fontSize = 10.sp,
                                fontFamily = Font.RussoOne,
                                color = Color(0xFF6C6C6C),
                            )
                        }

                        Row() {
                            Text(
                                text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                fontSize = 12.sp,
                                fontFamily = Font.RussoOne,
                                color = Color.Gray,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PasswordMiscProgressIndicator() {
    val progress by remember { mutableStateOf(0.75f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(120.dp)
    ) {
        CircularProgressIndicator(
            color = Color(0xFFE25C63),
            progress = { animatedProgress },
            trackColor = Color(0xFF1C1520),
            strokeWidth = 12.dp,
            strokeCap = StrokeCap.Round,
            modifier = Modifier.size(120.dp)
                    .scale(scaleX = -1f, scaleY = 1f)
        )
        Text(
            text = "82%",
            color = Color.White,
            fontFamily = Font.RussoOne,
            fontSize = 22.sp
        )
    }
}