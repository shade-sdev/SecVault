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
    private UpdateState _updateState;
    private int         _progressValue;
    private bool        _isInstalling;

    public string? StatusText { get; private set; }

    public UpdateState UpdateState
    {
        get => _updateState;
        set
        {
            this.RaiseAndSetIfChanged(ref _updateState, value);
            StatusText = value.GetDisplayText();
        }
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

    public ReactiveCommand<Unit, Unit> CheckForUpdatesCommand { get; }

    public ReactiveCommand<Unit, Unit> LaunchMainAppCommand { get; }

    public MainWindowViewModel()
    {
        CheckForUpdatesCommand = ReactiveCommand.CreateFromTask(CheckForUpdates);
        LaunchMainAppCommand   = ReactiveCommand.Create(LaunchMainApp);
        Task.Run(CheckForUpdates);
    }

    private async Task CheckForUpdates()
    {
        try
        {
            UpdateState = UpdateState.CheckingUpdates;
            var updateInfo =
                await
                    UpdaterModel
                        .GetUpdateInfo("https://raw.githubusercontent.com/yourusername/yourrepo/main/version.json");

            if (Version.Parse(updateInfo.Version) > Version.Parse(UpdaterModel.GetCurrentVersion()))
            {
                UpdateState = UpdateState.UpdateFound;
                await DownloadUpdate(updateInfo.DownloadUrl);
            }
            else
            {
                UpdateState = UpdateState.UpToDate;
                LaunchMainApp();
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.Message, ex.StackTrace);
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

            UpdateState = UpdateState.InstallationComplete;
            UpdaterModel.ExtractAndInstall(Path.Combine(tempFolder, "update.zip"), AppContext.BaseDirectory);

            LaunchMainApp();
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.Message, ex.StackTrace);
            UpdateState = UpdateState.InstallationComplete;
        }
        finally
        {
            IsInstalling = false;
        }
    }

    private static void LaunchMainApp()
    {
        Process.Start(Path.Combine(AppContext.BaseDirectory, "SecVault.exe"));
        Environment.Exit(0);
    }
    
}