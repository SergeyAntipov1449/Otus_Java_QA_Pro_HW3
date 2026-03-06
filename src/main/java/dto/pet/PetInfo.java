
package dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetInfo {
  private Category category;
  private Long id;
  private String name;
  private List<String> photoUrls;
  private String status;
  private List<Tag> tags;
}
