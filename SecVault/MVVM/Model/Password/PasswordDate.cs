namespace SecVault.MVVM.Model.Password;

public class PasswordDate
{
    public DateTime? CreatedDate { get; set; } = DateTime.Now;

    public DateTime? LastUpdatedDate { get; set; }
}