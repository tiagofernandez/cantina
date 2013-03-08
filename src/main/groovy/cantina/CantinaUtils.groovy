package cantina

import java.text.SimpleDateFormat

class CantinaUtils {

  static String currentDate(format) {
    def formatter = new SimpleDateFormat(format)
    formatter.format(new Date())
  }

  static String truncate(value, max) {
    if (value && value.length() > max) {
      value = "${value.substring(0, max)}..."
    }
    return value
  }
}
