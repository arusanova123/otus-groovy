import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.streaming.SXSSFRow
import org.apache.poi.xssf.streaming.SXSSFSheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook

class XlsxSpec {
    String fileName
    SXSSFWorkbook workbook
    SXSSFSheet sheet
    SXSSFRow row
    Cell cell
    def value
    int idx = 0
    def backgroundColor
    def color

    void sheet (int idx=0, String sheetName = "", Closure cl) {
        if (sheetName == "")
            sheetName = "Лист $idx"
        sheet = workbook.createSheet(sheetName)
        def rehydrate = cl.rehydrate(this, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        rehydrate ()
        cl
    }

    void row (int idx=0, Closure cl) {
        row = sheet.createRow(idx)
        def rehydrate = cl.rehydrate(this, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        rehydrate ()
        cl
    }

    void cell (Closure cl) {
        def rehydrate = cl.rehydrate(this, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        cell = null
        rehydrate ()
        cl.call()
        if (!cell) { // не было style
            cell = row.createCell(idx)
            cell.setCellValue(value)
        }
    }

    void style (Closure cl) {
        cell = row.createCell(idx)
        cell.setCellValue(value)
        backgroundColor = null
        color = null
        this.with (cl)
        CellStyle style = workbook.createCellStyle()
        if (color) {
            Font font = workbook.createFont()
            font.setColor(IndexedColors."${color.toUpperCase()}".index)
            style.setFont(font)
        }
        if (backgroundColor) {
            style.setFillForegroundColor(IndexedColors."${backgroundColor.toUpperCase()}".index)
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND)
        }
        cell.setCellStyle(style)
    }

    void build () {
        FileOutputStream fos = new FileOutputStream(new File(fileName))
        try {
            workbook.write(fos)
            workbook.close()
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }

    static void xlsx (String fileName, @DelegatesTo (XlsxSpec) Closure closure) {
        def xlsx = new XlsxSpec()
        xlsx.workbook = new SXSSFWorkbook()
        if (!fileName.endsWith(".xlsx"))
            fileName += ".xlsx"
        xlsx.fileName = fileName
        def rehydrate = closure.rehydrate(xlsx, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        rehydrate ()
        closure
        xlsx.build()
    }
}
