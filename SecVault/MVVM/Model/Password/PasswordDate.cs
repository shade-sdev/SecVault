using System.ComponentModel.DataAnnotations.Schema;

namespace SecVault.MVVM.Model.Password;

[ComplexType]
public class PasswordDate
{
    [Column("creation_date")] public DateTime? CreatedDate { get; set; } = DateTime.Now;

    [Column("last_updated_date")] public DateTime? LastUpdatedDate { get; set; } = DateTime.Now;
}