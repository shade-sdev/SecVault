using System;
using System.IO;
using System.IO.Compression;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace SecVaultUpdater.Models;

public static class UpdaterModel
{
    
    public const string UpdateJsonUrl = "https://raw.githubusercontent.com/shade-sdev/SecVault/refs/heads/master/update.json";
    
    public static async Task<VersionInfo> GetUpdateInfo(string url)
    {
        var client = new HttpClient();
        var json   = await client.GetStringAsync(url);
        return JsonConvert.DeserializeObject<VersionInfo>(json) ??
               throw new InvalidOperationException();
    }

    public static async Task DownloadAndUpdate(string      downloadUrl,
                                               string      destinationFolder,
                                               Action<int> progressCallback)
    {
        var client     = new HttpClient();
        var response   = await client.GetAsync(downloadUrl);
        var totalBytes = response.Content.Headers.ContentLength ?? 0;
        var buffer     = new byte[8192];

        await using var stream     = await response.Content.ReadAsStreamAsync();
        await using var fileStream = new FileStream(Path.Combine(destinationFolder, "update.zip"), FileMode.Create);
        int             bytesRead;
        long            bytesDownloaded = 0;
        while ((bytesRead = await stream.ReadAsync(buffer)) > 0)
        {
            await fileStream.WriteAsync(buffer.AsMemory(0, bytesRead));
            bytesDownloaded += bytesRead;
            progressCallback((int)((bytesDownloaded / (double)totalBytes) * 100));
        }
    }

    public static void ExtractAndInstall(string zipFilePath, string installDir)
    {
        ZipFile.ExtractToDirectory(zipFilePath, installDir);
    }
    
    public static string GetCurrentVersion()
    {
        try
        {
            var jsonPath = Path.Combine(AppContext.BaseDirectory, "app.json");
            if (!File.Exists(jsonPath)) return "0.0.0";
            var json    = File.ReadAllText(jsonPath);
            var appInfo = JsonConvert.DeserializeObject<AppInfo>(json);
            return appInfo?.Version ?? "0.0.0";
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error reading version file: {ex.Message}");
            return "0.0.0";
        }
    }
    
}