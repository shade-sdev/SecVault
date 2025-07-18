package core.windows

import com.sun.jna.platform.win32.Advapi32
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinBase
import com.sun.jna.platform.win32.WinNT

object WindowsAuth {

    fun authenticate(username: String, password: String, domain: String = "."): Boolean {
        val handle = WinNT.HANDLEByReference()
        val result = Advapi32.INSTANCE.LogonUser(
            username,
            domain,
            password,
            WinBase.LOGON32_LOGON_INTERACTIVE,
            WinBase.LOGON32_PROVIDER_DEFAULT,
            handle
        )

        if (result) {
            Kernel32.INSTANCE.CloseHandle(handle.value)
        }

        return result
    }

}