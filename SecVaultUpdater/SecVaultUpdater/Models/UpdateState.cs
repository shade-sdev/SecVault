using System.ComponentModel;

namespace SecVaultUpdater.Models;

public enum UpdateState
{
    CheckingUpdates,
    UpdateFound,
    UpToDate,
    Downloading,
    InstallationComplete,
    InstallationFailed
}

public static class UpdateStateExtensions
{
    public static string GetDisplayText(this UpdateState state) => state switch
                                                                   {
                                                                       UpdateState.CheckingUpdates   => "Checking for updates...",
                                                                       UpdateState.UpdateFound      => "Update found! Downloading...",
                                                                       UpdateState.UpToDate        => "You are up-to-date!",
                                                                       UpdateState.Downloading     => "Downloading update...",
                                                                       UpdateState.InstallationComplete => "Installation complete! Launching app...",
                                                                       UpdateState.InstallationFailed => "Installation failed.",
                                                                       _                           => string.Empty
                                                                   };
}
