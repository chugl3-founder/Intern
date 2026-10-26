import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ChuXe {
    private String cmnd;
    private String hoTen;
    private String email;

    public ChuXe(String cmnd, String hoTen, String email) {
        this.cmnd = cmnd;
        this.hoTen = hoTen;
        this.email = email;
    }

    public String getCmnd() { return cmnd; }
    public String getHoTen() { return hoTen; }

    @Override
    public String toString() {
        return hoTen + " (CMND: " + cmnd + ", Email: " + email + ")";
    }
}

// 2. Lop cha Phuong Tien
abstract class PhuongTien {
    protected String bienSo;
    protected String hangSanXuat;
    protected int namSanXuat;
    protected String mauXe;
    protected ChuXe chuXe;

    public PhuongTien(String bienSo, String hangSanXuat, int namSanXuat, String mauXe, ChuXe chuXe) {
        this.bienSo = bienSo;
        this.hangSanXuat = hangSanXuat;
        this.namSanXuat = namSanXuat;
        this.mauXe = mauXe;
        this.chuXe = chuXe;
    }

    public String getBienSo() { return bienSo; }
    public String getHangSanXuat() { return hangSanXuat; }
    public ChuXe getChuXe() { return chuXe; }

    @Override
    public String toString() {
        return "Bien so: " + bienSo + " | Hang: " + hangSanXuat + " | Nam SX: " + namSanXuat 
                + " | Mau: " + mauXe + " | Chu xe: " + chuXe;
    }
}

// 3. Cac Lop Con
class XeOto extends PhuongTien {
    private int soChoNgoi;
    private String kieuDongCo;

    public XeOto(String bienSo, String hangSanXuat, int namSanXuat, String mauXe, ChuXe chuXe, 
                 int soChoNgoi, String kieuDongCo) {
        super(bienSo, hangSanXuat, namSanXuat, mauXe, chuXe);
        this.soChoNgoi = soChoNgoi;
        this.kieuDongCo = kieuDongCo;
    }

    @Override
    public String toString() {
        return "[O TO] " + super.toString() + " | So cho: " + soChoNgoi + " | Dong co: " + kieuDongCo;
    }
}

class XeMay extends PhuongTien {
    private double dungTich;

    public XeMay(String bienSo, String hangSanXuat, int namSanXuat, String mauXe, ChuXe chuXe, double dungTich) {
        super(bienSo, hangSanXuat, namSanXuat, mauXe, chuXe);
        this.dungTich = dungTich;
    }

    @Override
    public String toString() {
        return "[XE MAY] " + super.toString() + " | Dung tich: " + dungTich + "cc";
    }
}

class XeTai extends PhuongTien {
    private double trongTai;

    public XeTai(String bienSo, String hangSanXuat, int namSanXuat, String mauXe, ChuXe chuXe, double trongTai) {
        super(bienSo, hangSanXuat, namSanXuat, mauXe, chuXe);
        this.trongTai = trongTai;
    }

    @Override
    public String toString() {
        return "[XE TAI] " + super.toString() + " | Trong tai: " + trongTai + " tan";
    }
}

class QuanLyPhuongTien {
    private List<PhuongTien> danhSach = new ArrayList<>();
    
    //1. them phuong tien
    public void themPhuongTien(PhuongTien pt){
        danhSach.add(pt);
        System.out.println("da them phuong tien co bien so: " + pt.getBienSo());
    }
    //2. tim phuong tien theo bien so
    public PhuongTien timTheoBienSo(String bs){
        for(PhuongTien item : danhSach){
            if(item.getBienSo().equalsIgnoreCase(bs)){
                return item;
            }
        }
        return null;
    }
    //3. tim danh sach xe theo so can cuoc cong dan cua chu xe
    public List<PhuongTien> timTheoCmndChuXe(String cmnd){
        List<PhuongTien> res = new ArrayList<>();
        for(PhuongTien item : danhSach){
            if(item.getChuXe().getCmnd().equalsIgnoreCase(cmnd)){
                res.add(item);
            }
        }
        return res;
    }
    //4. xoa xe theo hang
    public void xoaTheoHang(String hsx){
        danhSach.removeIf(pt -> pt.getHangSanXuat().equalsIgnoreCase(hsx));
        System.out.println(" Da xoa toan bo xe thuoc hang: " + hsx);
    }
     // 5. Chi ra hang san xuat co nhieu xe nhat
    public String hangCoNhieuXeNhat() {
        if (danhSach.isEmpty()) return "Danh sach rong";
        Map<String, Integer> mapCount = new HashMap<>();
        for (PhuongTien pt : danhSach) {
            String hang = pt.getHangSanXuat();
            mapCount.put(hang, mapCount.getOrDefault(hang, 0) + 1);
        }

        String maxHang = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : mapCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxHang = entry.getKey();
            }
        }
        return maxHang + " (" + maxCount + " xe)";
    }

    // 6. Sap xep phuong tien giam dan theo bien so xe
    public void sapXepGiamDanTheoBienSo() {
        Collections.sort(danhSach, new Comparator<PhuongTien>() {
            @Override
            public int compare(PhuongTien p1, PhuongTien p2) {
                return p2.getBienSo().compareTo(p1.getBienSo());
            }
        });
        System.out.println("🔄 Da sap xep danh sach giam dan theo bien so.");
    }

    // 7. Thong ke so luong tung loai xe
    public void thongKeLoaiXe() {
        int oto = 0, xeMay = 0, xeTai = 0;
        for (PhuongTien pt : danhSach) {
            if (pt instanceof XeOto) oto++;
            else if (pt instanceof XeMay) xeMay++;
            else if (pt instanceof XeTai) xeTai++;
        }
        System.out.println("\n=== THONG KE LOAI XE ===");
        System.out.println("O to: " + oto + " xe | Xe may: " + xeMay + " xe | Xe tai: " + xeTai + " xe");
    }

    public void hienThiDanhSach() {
        System.out.println("\n=== DANH SACH PHUONG TIEN GIAO THONG ===");
        for (PhuongTien pt : danhSach) {
            System.out.println(pt);
        }
    }
}
public class B3 {
    public static void main(String[] args) {
        QuanLyPhuongTien ql = new QuanLyPhuongTien();

        ChuXe c1 = new ChuXe("123456789", "Nguyen Van A", "a@gmail.com");
        ChuXe c2 = new ChuXe("987654321", "Tran Thi B", "b@gmail.com");

        ql.themPhuongTien(new XeOto("30A-12345", "Toyota", 2021, "Den", c1, 5, "V6"));
        ql.themPhuongTien(new XeMay("29C1-88888", "Honda", 2022, "Trang", c1, 150));
        ql.themPhuongTien(new XeTai("88C-55555", "Isuzu", 2019, "Xanh", c2, 10.5));
        ql.themPhuongTien(new XeMay("29D1-99999", "Honda", 2023, "Do", c2, 125));

        ql.hienThiDanhSach();

        // Thong ke
        ql.thongKeLoaiXe();

        // Hang co nhieu xe nhat
        System.out.println("\nHang co nhieu xe nhat: " + ql.hangCoNhieuXeNhat());

        // Tim kiem theo bien so
        System.out.println("\nTim xe 30A-12345: " + ql.timTheoBienSo("30A-12345"));

        // Sap xep
        ql.sapXepGiamDanTheoBienSo();
        ql.hienThiDanhSach();
    }
    
}