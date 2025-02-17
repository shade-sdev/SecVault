package core.service

import java.awt.Image
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon

/**
 * Service for managing system tray notifications.
 */
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

    /**
     * Displays a notification message in the system tray.
     *
     * @param title The title of the notification.
     * @param message The message content of the notification.
     * @param messageType The type of the message (e.g., INFO, WARNING, ERROR).
     */
    fun showNotification(title: String, message: String, messageType: TrayIcon.MessageType) {
        trayIcon?.displayMessage(title, message, messageType)
    }

}
