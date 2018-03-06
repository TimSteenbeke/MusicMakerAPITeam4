package be.kdg.ip.domain;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "composition")
public class Composition {
        @Id
        @GeneratedValue
        @Column(name="MuziekstukId",nullable = false)
        private int MuziekstukId;

        @Column
        private String titel;
        @Column
        private String artist;
        @Column
        private String language;
        @Column
        private String genre;
        @Column
        private String subject;
        @Column
        private String instrumentType;
        @Column
        private String link;
        @Column
        private String fileFormat;
        @Lob
        @Column
        private byte[] content;

        public Composition(){

        }

        public Composition(String titel, String artist, String language, String genre, String subject, String instrumentType,String link, String fileFormat, byte[] content) {
            this.titel = titel;
            this.artist = artist;
            this.language = language;
            this.genre = genre;
            this.subject = subject;
            this.instrumentType = instrumentType;
            this.link = link;
            this.fileFormat = fileFormat;
            this.content = content;
        }

        public int getMuziekstukId() {
            return MuziekstukId;
        }

        public void setMuziekstukId(int muziekstukId) {
            MuziekstukId = muziekstukId;
        }

        public String getTitel() {
            return titel;
        }

        public void setTitel(String titel) {
            this.titel = titel;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getInstrumentType() {
            return instrumentType;
        }

        public void setInstrumentType(String instrumentType) {
            this.instrumentType = instrumentType;
        }

        public byte[] getContent() {
            return content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
}
