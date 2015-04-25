package ot;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

interface Serialize {
    public Serialize decode(ByteBuffer data) throws Exception;
}

public class Data implements Serialize {
    public final static int DATA_LENGTH = 228;
    public String stx;	// STX 1 - &H02
    public String data_no;	// DATA NO 测量号码 3 右 ﾛﾛ1
    public String sample_id;	// PATIENT/SAMPLE ID 15 左 AB0123456789
    public String operator;	// OPERATOR ID 8 左 SUZUKIﾛﾛ
    public String diagnostic_date;	// DIAGNOSTIC DATE 測定日 8 - 11/08/09
    public String diagnostic_time;	// DIAGNOSTIC TIME 測定時間 8 - 10:10:05
    public String test_name1;	// TEST NAME1 产品的中文名称1 20 左 GB font code
    public String test_name2;	// TEST NAME2 产品的中文名称2 20 左 GB font code
    public String test_name3;	// TEST NAME3 产品的中文名称3 20 左 GB font code
    public String lot_no;	// LOT NO 6 - 011105
    public String expiration;	// EXPIRATION 使用期限 8 - 12/05/31
    public String mabs1;	// MABS1 TEST1 mABS値 6 右 ﾛﾛ85.6
    public String mabs2;	// MABS2 TEST2 mABS値 6 右 ﾛﾛ85.6
    public String mabs3;	// MABS3 TEST3 mABS値 6 右 ﾛﾛ85.6
    public String mabs4;	// MABS4 CONT mABS値 6 右 ﾛﾛ85.6
    public String concentration1;	// CONCENTRATION1 濃度(T/C的值) 6 右 ﾛﾛ12.3
    public String concentration2;	// CONCENTRATION2 濃度(T/C的值) 6 右 ﾛﾛ45.6
    public String concentration3;	// CONCENTRATION3 濃度(T/C的值) 6 右 ﾛ789.0
    public String unit1;	// UNIT1 単位 10 左 ex. ng/ml
    public String unit2;	// UNIT2 単位 10 左 ex. ng/ml
    public String unit3;	// UNIT3 単位 10 左 ex. ng/ml
    public String judge1;	// JUDGE1 判定 4 左 ex. POS
    public String judge2;	// JUDGE2 判定 4 左 ex. POS
    public String judge3;	// JUDGE3 判定 4 左 ex. POS
    public String reserve1;	// RESERVE1 予備 15 左 ﾛﾛﾛﾛﾛﾛﾛﾛﾛﾛﾛﾛﾛﾛﾛ
    public String alarm;	// ALARM 2 左 MN D 稀釈検体 H 溶血検体 DH 稀釈・溶血検体 MX 濃度MAX以上 MN 濃度MIN以下
    public String reserve2;	// RESERVE2 予備 8 左 ﾛﾛﾛﾛﾛﾛﾛﾛ
    public String ext;	// EXT 1 - &H03
    public String bcc;	// BCC check sum 1 - EX-OR Sum

    @Override
    public Data decode(ByteBuffer data) throws Exception {
        data.flip();
        stx = getData(data, 1);
        data_no = getData(data, 3);
        sample_id = getData(data, 15);
        operator = getData(data, 8);
        diagnostic_date = getData(data, 8);
        diagnostic_time = getData(data, 8);
        test_name1 = getData(data, 20);
        test_name2 = getData(data, 20);
        test_name3 = getData(data, 20);
        lot_no = getData(data, 6);
        expiration = getData(data, 8);
        mabs1 = getData(data, 6);
        mabs2 = getData(data, 6);
        mabs3 = getData(data, 6);
        mabs4 = getData(data, 6);
        concentration1 = getData(data, 6);
        concentration2 = getData(data, 6);
        concentration3 = getData(data, 6);
        unit1 = getData(data, 10);
        unit2 = getData(data, 10);
        unit3 = getData(data, 10);
        judge1 = getData(data, 4);
        judge2 = getData(data, 4);
        judge3 = getData(data, 4);
        reserve1 = getData(data, 15);
        alarm = getData(data, 2);
        reserve2 = getData(data, 8);
        ext = getData(data, 1);
        bcc = getData(data, 1);
        return this;
    }

    private String getData(ByteBuffer data, int count) throws UnsupportedEncodingException {
        byte[] bs = new byte[count];
        for (int i=0; i<count; i++) {
            bs[i] = (byte) data.get();
        }
        return new String(bs, "GBK");
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString()).append(" [")
            .append(" stx=").append(stx)
            .append(" data_no=").append(data_no)
            .append(" sample_id=").append(sample_id)
            .append(" operator=").append(operator)
            .append(" diagnostic_date=").append(diagnostic_date)
            .append(" diagnostic_time=").append(diagnostic_time)
            .append(" test_name1=").append(test_name1)
            .append(" test_name2=").append(test_name2)
            .append(" test_name3=").append(test_name3)
            .append(" lot_no=").append(lot_no)
            .append(" expiration=").append(expiration)
            .append(" mabs1=").append(mabs1)
            .append(" mabs2=").append(mabs2)
            .append(" mabs3=").append(mabs3)
            .append(" mabs4=").append(mabs4)
            .append(" concentration1=").append(concentration1)
            .append(" concentration2=").append(concentration2)
            .append(" concentration3=").append(concentration3)
            .append(" unit1=").append(unit1)
            .append(" unit2=").append(unit2)
            .append(" unit3=").append(unit3)
            .append(" judge1=").append(judge1)
            .append(" judge2=").append(judge2)
            .append(" judge3=").append(judge3)
            .append(" reserve1=").append(reserve1)
            .append(" alarm=").append(alarm)
            .append(" reserve2=").append(reserve2)
            .append(" ext=").append(ext)
            .append(" bcc=").append(bcc)
            .append(" ]").toString();
    }
}
