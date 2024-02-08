using Microsoft.EntityFrameworkCore;
using SecVault.MVVM.Model.Password;

namespace SecVault.Data;

public class PasswordContext : DbContext
{
    private readonly string _path = Environment.CurrentDirectory + @"\Password.db";
    
    protected override void OnConfiguring(DbContextOptionsBuilder options) => options.UseSqlite($"Data Source={_path}");
    
    public DbSet<Password>? Passwords { get; set; }
    
}