using System;
using System.Diagnostics;
using System.IO;
using System.Reactive;
using System.Threading.Tasks;
using ReactiveUI;
using SecVaultUpdater.Models;

namespace SecVaultUpdater.ViewModels;

public class MainWindowViewModel : ViewModelBase
{
    public ReactiveCommand<Unit, Unit> CheckForUpdatesCommand { get; }

    private string? _statusText;
    private int     _progressValue;
    private bool    _isInstalling;

    public string? StatusText
    {
        get => _statusText;
        set => this.RaiseAndSetIfChanged(ref _statusText, value);
    }

    public int ProgressValue
    {
        get => _progressValue;
        set => this.RaiseAndSetIfChanged(ref _progressValue, value);
    }

    public bool IsInstalling
    {
        get => _isInstalling;
        set => this.RaiseAndSetIfChanged(ref _isInstalling, value);
    }
    
    public MainWindowViewModel()
    {
        CheckForUpdatesCommand = ReactiveCommand.CreateFromTask(CheckForUpdates);
        Task.Run(CheckForUpdates);
    }

    private async Task CheckForUpdates()
    {
        try
        {
            StatusText = "Checking for updates...";
            var updateInfo =
                await
                    UpdaterModel
                        .GetUpdateInfo("https://raw.githubusercontent.com/yourusername/yourrepo/main/version.json");

            var currentVersion = "1.0.0"; // Replace this with actual version retrieval logic.
            if (Version.Parse(updateInfo.Version) > Version.Parse(currentVersion))
            {
                StatusText = "Update found! Downloading...";
                await DownloadUpdate(updateInfo.DownloadUrl);
            }
            else
            {
                StatusText = "You are up-to-date!";
            }
        }
        catch (Exception ex)
        {
            StatusText = $"Error: {ex.Message}";
        }
    }

    private async Task DownloadUpdate(string downloadUrl)
    {
        try
        {
            IsInstalling = true;
            var tempFolder = Path.GetTempPath();
            await UpdaterModel.DownloadAndUpdate(downloadUrl, tempFolder, progress => ProgressValue = progress);

            StatusText = "Installation complete! Launching app...";
            UpdaterModel.ExtractAndInstall(Path.Combine(tempFolder, "update.zip"), @"C:\path\to\installation");

            // Launch the Jetpack Compose app
            Process.Start(@"C:\path\to\installation\yourapp.exe");
            Environment.Exit(0); // Exit updater
        }
        catch (Exception ex)
        {
            StatusText = $"Installation failed: {ex.Message}";
        }
        finally
        {
            IsInstalling = false;
        }
    }
    
}