import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Message;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class BotUsers {
    private String chatId;
    private String language;
    private String step;
    private Message message;
}
