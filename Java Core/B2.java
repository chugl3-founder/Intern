import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

abstract class HangHoa {
    protected String maHang;
    protected String tenHang;
    protected int slTon;
    protected double donGia;

    public HangHoa(String mahang, String tenhang, int slton, double dongia) {
        if (mahang == null || mahang.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma hang khong duoc rong");
        }
        this.maHang = mahang;
        this.tenHang = tenhang;
        this.slTon = slton;
        this.donGia = dongia;
    }

    public String getMaHang() {
        return maHang;
    }

    public int getSoLuongTon() {
        return slTon;
    }

    public abstract double tinhVAT();

    public abstract String danhGiaMucDoTieuThu();

    @Override
    public String toString() {
        return "Ma: " + maHang + " | Ten: " + tenHang + " | Ton: " + slTon
                + " | Don Gia: " + donGia + " | VAT: " + tinhVAT()
                + " | Danh gia: " + danhGiaMucDoTieuThu();
    }

}

class HangThucPham extends HangHoa {
    private LocalDate ngaySanXuat;
    private LocalDate ngayHetHan;
    private String nhaCungCap;

    public HangThucPham(String maHang, String tenHang, int soLuongTon, double donGia,
            LocalDate ngaySanXuat, LocalDate ngayHetHan, String nhaCungCap) {
        super(maHang, tenHang, soLuongTon, donGia);
        if (ngayHetHan.isBefore(ngaySanXuat)) {
            throw new IllegalArgumentException("Ngay het han phai sau hoac bang ngay san xuat!");
        }
        this.ngaySanXuat = ngaySanXuat;
        this.ngayHetHan = ngayHetHan;
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public double tinhVAT() {
        return donGia * slTon * 0.05;
    }

    @Override
    public String danhGiaMucDoTieuThu() {
        // Con hang ton va da het han -> Kho ban
        if (slTon > 0 && LocalDate.now().isAfter(ngayHetHan)) {
            return "Kho ban (Da het han)";
        }
        return "Binh thuong";
    }

}

class HangDienMay extends HangHoa {
    private int thoiGianBaoHanhThang;
    private double congSuatKW;

    public HangDienMay(String maHang, String tenHang, int soLuongTon, double donGia,
            int thoiGianBaoHanhThang, double congSuatKW) {
        super(maHang, tenHang, soLuongTon, donGia);
        this.thoiGianBaoHanhThang = Math.max(0, thoiGianBaoHanhThang);
        this.congSuatKW = Math.max(0, congSuatKW);
    }

    @Override
    public double tinhVAT() {
        return donGia * slTon * 0.10; // 10%
    }

    @Override
    public String danhGiaMucDoTieuThu() {
        // So luong ton < 3 -> Ban duoc
        if (slTon < 3) {
            return "Ban duoc";
        }
        return "Binh thuong";
    }
}

class HangSanhSu extends HangHoa {
    private String nhaSanXuat;
    private LocalDate ngayNhapKho;

    public HangSanhSu(String maHang, String tenHang, int soLuongTon, double donGia,
            String nhaSanXuat, LocalDate ngayNhapKho) {
        super(maHang, tenHang, soLuongTon, donGia);
        this.nhaSanXuat = nhaSanXuat;
        this.ngayNhapKho = ngayNhapKho;
    }

    @Override
    public double tinhVAT() {
        return donGia * slTon * 0.10;
    }

    @Override
    public String danhGiaMucDoTieuThu() {
        long soNgayLuuKho = ChronoUnit.DAYS.between(ngayNhapKho, LocalDate.now());
        // So luong ton > 50 va luu kho > 10 ngay -> Ban cham
        if (slTon > 50 && soNgayLuuKho > 10) {
            return "Ban cham";
        }
        return "Binh thuong";
    }
}

class QLDSHH {
    private List<HangHoa> danhSach;

    public QLDSHH() {
        this.danhSach = new ArrayList<>();
    }

    public boolean themHangHoa(HangHoa hh) {
        for (HangHoa item : danhSach) {
            if (item.getMaHang().equalsIgnoreCase(hh.getMaHang())) {
                System.out.println("Them that bai. Ma hang da bi trung lap (" + hh.getMaHang() + ")!");
                return false;
            }
        }
        danhSach.add(hh);
        System.out.println("Da them hang hoa!");
        return true;
    }

    public void hienThiToanBo() {
        System.out.println("\n=== DANH SACH HANG HOA SIEU THI   ===");
        for (HangHoa hh : danhSach) {
            System.out.println(hh);
        }
    }
}

public class B2 {
    public static void main(String[] args) {
        QLDSHH ql = new QLDSHH();

        // Them hang thuc pham
        HangHoa tp1 = new HangThucPham("TP01", "Sua tuoi", 10, 15000,
                LocalDate.now().minusDays(20), LocalDate.now().minusDays(2), "Vinamilk");
        ql.themHangHoa(tp1);

        // Them hang dien may
        HangHoa dm1 = new HangDienMay("DM01", "Tu lanh", 2, 8000000, 24, 1.5);
        ql.themHangHoa(dm1);

        // Them hang sanh su
        HangHoa ss1 = new HangSanhSu("SS01", "Bo bat dia", 60, 200000,
                "Bat Trang", LocalDate.now().minusDays(15));
        ql.themHangHoa(ss1);

        // Thu them trung ma
        HangHoa dm2 = new HangDienMay("DM01", "May giat", 5, 5000000, 12, 1.0);
        ql.themHangHoa(dm2);

        ql.hienThiToanBo();
    }
}
