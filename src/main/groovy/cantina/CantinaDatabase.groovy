package cantina

import org.h2.tools.Csv
import org.h2.tools.SimpleResultSet

import java.sql.Types
import java.sql.ResultSet

abstract class CantinaDatabase {

  protected final def columns = []
  protected final def rows = []

  private final String filePath

  CantinaDatabase(filePath, cols) {
    this.filePath = filePath
    cols.each { this.columns << it }
  }

  protected void persist() {
    def rs = new SimpleResultSet()
    columns.each { column ->
      rs.addColumn(column, Types.VARCHAR, 255, 0)
    }
    rows.each { row ->
      rs.addRow(row as String[])
    }
    new Csv().write(filePath, rs, null)
  }

  protected void refreshRows() {
    rows.clear()
    if (new File(filePath).exists()) {
      ResultSet rs = new Csv().read(filePath, null, null)
      while (rs.next()) {
        rows << columns.inject([], { row, column ->
          row << rs.getString(column)
        })
      }
      rs.close()
    }
  }
}
