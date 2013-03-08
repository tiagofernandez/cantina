package cantina

import java.util.zip.CRC32

import static cantina.CantinaUtils.*

class CommentsDatabase extends CantinaDatabase {

  CommentsDatabase(filePath) {
    super(filePath, ['NAME', 'COMMENT', 'TIMESTAMP', 'TOKEN'])
  }

  List allComments() {
    refreshRows()
    rows.findAll { check(it) }
  }

  void addComment(name, comment) {
    refreshRows()
    rows << [name, comment, currentDate('HH:mm'), computeToken(comment)]
    persist()
  }

  private boolean check(row) {
    row[3] == computeToken(row[1])
  }

  private String computeToken(comment) {
    def sha = ''
    def target = comment.replaceAll('\\s', '').toLowerCase().bytes
    for (int i = 0; i < target.length; i++) {
      sha += Integer.toString((target[i] & 0xff) + 0x100, 16).substring(1)
    }
    def shaBytes = sha.bytes
    def crc32 = new CRC32()
    crc32.update(sha.bytes, 0, shaBytes.length)
    return "${truncate(sha, 32)}+${crc32.value}"
  }
}
