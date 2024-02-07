namespace SecVault.MVVM.Model.Password;

public class CardInfo
{
    public string? CardNumber { get; set; }
    
    public string? Pin { get; set; }
    
    public string? CardNote { get; set; }
    
    public DateTime? CardExpiryDate { get; set; }
}