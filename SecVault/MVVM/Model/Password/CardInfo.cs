using System.ComponentModel.DataAnnotations.Schema;

namespace SecVault.MVVM.Model.Password;

[ComplexType]
public class CardInfo
{
    [Column("card_number")] 
    public string? CardNumber { get; set; }

    [Column("card_pin")] 
    public string? Pin { get; set; }

    [Column("card_notes")] 
    public string? CardNote { get; set; }

    [Column("card_expiry_date")] 
    public DateTime? CardExpiryDate { get; set; }
}