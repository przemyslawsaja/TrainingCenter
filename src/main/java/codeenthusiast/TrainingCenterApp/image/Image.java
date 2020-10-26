package codeenthusiast.TrainingCenterApp.image;

import codeenthusiast.TrainingCenterApp.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class Image extends AbstractEntity {



    private String filePath;

    private String fileUrl;

    public Image(String filePath, String fileUrl) {
        this.filePath = filePath;
        this.fileUrl = fileUrl;
    }

}