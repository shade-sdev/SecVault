namespace SecVault.MVVM.Model.Password;

public class PasswordInfo
{
    public string? Username { get; set; }
    
    public string? Email { get; set; }
    
    public string? EncPassword { get; set; }
    
    public bool IsFavourite { get; set; } = false;
}