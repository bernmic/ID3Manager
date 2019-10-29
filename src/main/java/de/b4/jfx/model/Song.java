package de.b4.jfx.model;

import com.mpatric.mp3agic.*;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static com.mpatric.mp3agic.AbstractID3v2Tag.*;

public class Song {
  public enum Field {
    FILENAME, PATH, FILETYPE, DIRTY, YEAR, TITLE, ARTIST, ALBUM, GENRE, TRACK, COMMENT, LENGTH, BITRATE, SAMPLINGRATE, CHANNELS, VBR, ID3VERSION
  }

  private String filename;
  private String path;
  private String fileType;
  private boolean dirty;
  private Mp3File mp3File;
  private File file;

  private Song() {
  }

  public Song(File f) {
    dirty = false;
    file = f;
    try {
      Mp3File a = new Mp3File(f.getAbsolutePath());
      this.mp3File = a;
      filename = f.getName();
      path = f.getAbsolutePath().substring(0, f.getAbsolutePath().indexOf(f.getName()));
      setFileType();
      //tagDumper();
    } catch (UnsupportedTagException | InvalidDataException | IOException e) {
      // TODO Auto-generated catch block
      System.out.println(e.getMessage() + " in file " + f.getName());
      file = null;
      // e.printStackTrace();
    }
  }

  public String getYear() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getYear());
  }

  public void setYear(String year) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (year == null || year.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_YEAR);
      return;
    }
    mp3File.getId3v2Tag().setYear(year);
  }

  public String getTitle() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getTitle());
  }

  public void setTitle(String title) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (title == null || title.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_TITLE);
      return;
    }
    mp3File.getId3v2Tag().setTitle(title);
  }

  public String getArtist() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getArtist());
  }

  public void setArtist(String artist) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (artist == null || artist.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_ARTIST);
      return;
    }
    mp3File.getId3v2Tag().setArtist(artist);
  }

  public String getOriginalArtist() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getOriginalArtist());
  }

  public void setOriginalArtist(String artist) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (artist == null || artist.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_ORIGINAL_ARTIST);
      return;
    }
    mp3File.getId3v2Tag().setOriginalArtist(artist);
  }

  public String getAlbumArtist() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getAlbumArtist());
  }

  public void setAlbumArtist(String artist) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (artist == null || artist.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_ALBUM_ARTIST);
      return;
    }
    mp3File.getId3v2Tag().setAlbumArtist(artist);
  }

  public String getAlbum() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getAlbum());
  }

  public void setAlbum(String album) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (album == null || album.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_ALBUM);
      return;
    }
    mp3File.getId3v2Tag().setAlbum(album);
  }

  public String getGenre() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getGenreDescription());
  }

  public void setGenre(String genre) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (genre == null || genre.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_GENRE);
      return;
    }
    mp3File.getId3v2Tag().setGenreDescription(genre);
  }

  public void setComposer(String composer) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (composer == null || composer.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_COMPOSER);
      return;
    }
    mp3File.getId3v2Tag().setComposer(composer);
  }

  public void setCD(String cd) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (cd == null || cd.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_PART_OF_SET);
      return;
    }
    mp3File.getId3v2Tag().setPartOfSet(cd);
  }

  public String getTrack() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getTrack());
  }

  public void setTrack(String track) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (track == null || track.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_TRACK);
      return;
    }
    mp3File.getId3v2Tag().setTrack(track);
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    if (!filename.equals(this.filename)) {
      dirty = true;
    }
    this.filename = filename;
    setFileType();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public boolean isDirty() {
    return dirty;
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }

  private void setFileType() {
    if (filename.toLowerCase().endsWith(".mp3")) {
      fileType = "mp3";
    } else if (filename.toLowerCase().endsWith(".mp4")) {
      fileType = "mp4";
    } else if (filename.toLowerCase().endsWith(".mpc")) {
      fileType = "mpc";
    } else if (filename.toLowerCase().endsWith(".asf")) {
      fileType = "asf";
    } else if (filename.toLowerCase().endsWith(".flac")) {
      fileType = "flac";
    } else if (filename.toLowerCase().endsWith(".wav")) {
      fileType = "wav";
    } else if (filename.toLowerCase().endsWith(".ogg")) {
      fileType = "ogg";
    } else {
      fileType = "unknown";
    }
  }

  public String getCD() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getPartOfSet());
  }

  public String getComposer() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(mp3File.getId3v2Tag().getComposer());
  }

  public String getBPM() {
    if (!mp3File.hasId3v2Tag())
      return "";
    return nullSafe(getFrame("TBPM"));
  }

  public String getLength() {
    return Song.getDurationAsString((int) mp3File.getLengthInSeconds());
  }

  public Long getLengthInSeconds() {
    return mp3File.getLengthInSeconds();
  }

  public File getFile() {
    return file;
  }

  public static boolean isSupportedFormat(String name) {
    return name.endsWith(".mp3") || name.endsWith(".mp4") || name.endsWith(".flac") || name.endsWith(".ogg") || name.endsWith(".wav")
            || name.endsWith(".mpc") || name.endsWith(".asf");
  }

  public int getBitrate() {
    return mp3File.getBitrate();
  }

  public int getSamplingrate() {
    return mp3File.getSampleRate();
  }

  public String getChannels() {
    return nullSafe(mp3File.getChannelMode());
  }

  public boolean isVbr() {
    return mp3File.isVbr();
  }

  public String getID3Version() {
    String v = "";
    if (mp3File.hasId3v1Tag()) {
      v = "ID3v1." + mp3File.getId3v1Tag().getVersion();
      if (mp3File.hasId3v2Tag())
        v += ", ";
    }
    if (mp3File.hasId3v2Tag()) {
      if (mp3File.getId3v2Tag() instanceof ID3v22Tag) {
        v += "ID3v2.2";
      }
      if (mp3File.getId3v2Tag() instanceof ID3v23Tag) {
        v += "ID3v2.3";
      }
      if (mp3File.getId3v2Tag() instanceof ID3v24Tag) {
        v += "ID3v2.4";
      }
    }
    return v;
  }

  public Image getCover() {
    if (!mp3File.hasId3v2Tag())
      return null;
    if (mp3File.getId3v2Tag().getAlbumImage() == null)
      return null;
    Image image = new Image(new ByteArrayInputStream(mp3File.getId3v2Tag().getAlbumImage()));
    return image;
  }

  public byte[] getCoverData() {
    if (!mp3File.hasId3v2Tag())
      return null;
    // tagDumper();
    if (mp3File.getId3v2Tag().getAlbumImage() == null)
      return null;
    return mp3File.getId3v2Tag().getAlbumImage();
  }

  public void setCover(String coverfile) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (coverfile == null) {
      mp3File.getId3v2Tag().clearFrameSet("APIC");
      return;
    }
    try {
      Path path = Paths.get(coverfile);
      byte[] image = Files.readAllBytes(path);
      mp3File.getId3v2Tag().setAlbumImage(image, Song.getMimetype(coverfile));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void setCover(byte[] data, String mimetype) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (data == null) {
      mp3File.getId3v2Tag().clearFrameSet("APIC");
      return;
    }
    mp3File.getId3v2Tag().setAlbumImage(data, mimetype);
  }

  public void save() {
    try {
      mp3File.save(file.getAbsolutePath() + "XXX");
      file.delete();
      File dest = file;
      if (!filename.equals(file.getName())) {
        dest = new File(path + filename);
      }
      if (!new File(file.getAbsolutePath() + "XXX").renameTo(dest)) {
        System.err.println("Could not rename " + file.getAbsolutePath());
      }
      dirty = false;
    } catch (NotSupportedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void removeID3v1() {
    if (mp3File.hasId3v1Tag()) {
      mp3File.removeId3v1Tag();
      dirty = true;
    }
  }

  public void removeID3v2() {
    if (mp3File.hasId3v2Tag()) {
      mp3File.removeId3v2Tag();
      dirty = true;
    }
  }

  public void setComments(String comments) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (comments == null || comments.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_COMMENT);
      return;
    }
    mp3File.getId3v2Tag().setComment(comments);

  }

  public String getComments() {
    if (!mp3File.hasId3v2Tag())
      return null;
    return mp3File.getId3v2Tag().getComment();
  }

  public void setEncoder(String encoder) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (encoder == null || encoder.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_ENCODER);
      return;
    }
    mp3File.getId3v2Tag().setEncoder(encoder);

  }

  public String getEncoder() {
    if (!mp3File.hasId3v2Tag())
      return null;
    return mp3File.getId3v2Tag().getEncoder();
  }

  public void setCopyright(String copyright) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (copyright == null || copyright.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_COPYRIGHT);
      return;
    }
    mp3File.getId3v2Tag().setCopyright(copyright);

  }

  public String getCopyright() {
    if (!mp3File.hasId3v2Tag())
      return null;
    return mp3File.getId3v2Tag().getCopyright();
  }

  public void setPublisher(String publisher) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (publisher == null || publisher.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_PUBLISHER);
      return;
    }
    mp3File.getId3v2Tag().setPublisher(publisher);

  }

  public String getPublisher() {
    if (!mp3File.hasId3v2Tag())
      return null;
    return mp3File.getId3v2Tag().getPublisher();
  }

  public void setCompilation(boolean compilation) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    mp3File.getId3v2Tag().setCompilation(compilation);

  }

  public boolean getCompilation() {
    if (!mp3File.hasId3v2Tag())
      return false;
    return mp3File.getId3v2Tag().isCompilation();
  }

  public void setGrouping(String grouping) {
    if (!mp3File.hasId3v2Tag()) {
      mp3File.setId3v2Tag(new ID3v24Tag());
    }
    if (grouping == null || grouping.length() == 0) {
      mp3File.getId3v2Tag().clearFrameSet(ID_GROUPING);
      return;
    }
    mp3File.getId3v2Tag().setGrouping(grouping);

  }

  public String getGrouping() {
    if (!mp3File.hasId3v2Tag())
      return null;
    return mp3File.getId3v2Tag().getGrouping();
  }

  @SuppressWarnings("unused")
  private void tagDumper() {
    if (!mp3File.hasId3v2Tag())
      return;
    Set<String> keys = mp3File.getId3v2Tag().getFrameSets().keySet();
    for (String string : keys) {
      System.out.println(string);
    }
  }

  private String nullSafe(String s) {
    return s == null ? "" : s;
  }

  private String getFrame(String id) {
    if (!mp3File.hasId3v2Tag())
      return "";
    ID3v2FrameSet fs = mp3File.getId3v2Tag().getFrameSets().get(id);
    if (fs == null)
      return null;
    if (fs.getFrames().size() < 1)
      return null;
    byte[] b = fs.getFrames().get(0).getData();
    return BufferTools.byteBufferToStringIgnoringEncodingIssues(b, 3, b.length - 3);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Song song = new Song();
    song.dirty = dirty;
    song.filename = new String(filename);
    song.path = new String(path);
    song.file = new File(file.getAbsolutePath());
    song.fileType = new String(fileType);
    return song;
  }

  public static String getDurationAsString(int d) {
    int hours = d / 3600;
    d %= 3600;
    int min = d / 60;
    d %= 60;
    return hours > 0 ? String.format("%d:%02d:%02d", hours, min, d) : String.format("%d:%02d", min, d);
  }

  public static String getMimetype(String filename) {
    String extension = getExtension(filename);
    if (".gif".equalsIgnoreCase(extension)) {
      return "image/gif";
    }
    if (".jpg".equalsIgnoreCase(extension) || ".jpeg".equalsIgnoreCase(extension)) {
      return "image/jpeg";
    }
    if (".png".equalsIgnoreCase(extension)) {
      return "image/png";
    }
    return null;
  }

  public static String getExtension(String s) {
    if (s == null)
      return "";
    String separator = System.getProperty("file.separator");
    String filename;

    // Remove the path upto the filename.
    int lastSeparatorIndex = s.lastIndexOf(separator);
    if (lastSeparatorIndex == -1) {
      filename = s;
    } else {
      filename = s.substring(lastSeparatorIndex + 1);
    }

    // extract the extension.
    int extensionIndex = filename.lastIndexOf(".");
    if (extensionIndex == -1)
      return "";

    return filename.substring(extensionIndex);
  }
}
