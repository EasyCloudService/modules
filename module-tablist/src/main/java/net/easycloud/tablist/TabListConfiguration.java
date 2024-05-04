package net.easycloud.tablist;

import lombok.Getter;
import net.easycloud.api.conf.FileName;

@Getter
@FileName(name = "config")
public final class TabListConfiguration {
    private final String header;
    private final String footer;

    public TabListConfiguration() {
        this.header = "\n§8▶▷ §bEasyCloud §8» §7simplicity meets §b§operformance \n  §8◁ §bOnline §8» §7%online%§8/§7%max% §8| §bServer §8» §7%server% §8▷ \n";
        this.footer = "\n §8▶▷ §bDownload §8» §7easycloudservice.de \n §8▶▷ §bGithub §8» §7github.com/easycloudservice \n";
    }

    public TabListConfiguration(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }
}
