package com.katanemimena.backend.citizen;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Lob
    @Getter @Setter
    private byte[] data;
    public String fileName;
    public String fileType;

    public Document(String fileName, String fileType,byte[] data) {
        this.fileName=fileName;
        this.fileType=fileType;
        this.data = data;
    }
}
