package core.service

import java.awt.Image
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon

object TrayService {

    private var trayIcon: TrayIcon? = null
    private val tray = SystemTray.getSystemTray()

    init {
        val image = Toolkit.getDefaultToolkit().getImage(this::class.java.getResource("/assets/icon.png"))
        val scaledImage: Image = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH)

        trayIcon = TrayIcon(scaledImage, "Secvault")
        trayIcon?.toolTip = "Secvault"
        tray.add(trayIcon)
    }

    fun showNotification(title: String, message: String, messageType: TrayIcon.MessageType) {
        trayIcon?.displayMessage(title, message, messageType)
    }

}
