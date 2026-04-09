# 🍽️ Hôm nay ăn gì? (Food & Drink Randomizer)

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Material Design](https://img.shields.io/badge/Material--Design-3-blue?style=for-the-badge)

Một ứng dụng Android nhỏ gọn, thú vị giúp giải quyết câu hỏi muôn thuở: **"Hôm nay ăn gì? Uống gì?"**. Ứng dụng cung cấp các gợi ý ngẫu nhiên với giao diện thanh lịch, hiện đại và trải nghiệm người dùng (UX) mượt mà.

Dự án này được xây dựng nhằm mục đích áp dụng và củng cố các kiến thức về **Modern Android Development (MAD)**, đặc biệt là kiến trúc MVVM, View Binding và xử lý sự kiện tùy chỉnh.

## ✨ Tính năng nổi bật (Features)

* **🎲 Random thông minh:** Tự động gợi ý ngẫu nhiên món ăn và thức uống ngay khi mở ứng dụng.
* **🔄 Điều hướng xoay vòng (Circular Navigation):** Duyệt qua danh sách vô tận không có điểm dừng.
* **✨ Hiệu ứng mượt mà (Animations):** Hiệu ứng mờ tỏ (fade) và thu phóng (scale) bằng `ViewPropertyAnimator` mỗi khi dữ liệu thay đổi.
* **💎 Giao diện đẹp mắt:** Thiết kế chuẩn Material Design 3 với tone màu trắng ngà (off-white) & Trắng khói (White Smoke) hiện đại, sang trọng.



## 🛠️ Công nghệ & Kiến trúc (Tech Stack & Architecture)

Dự án tuân thủ nghiêm ngặt chuẩn **Clean Code** và kiến trúc **MVVM**.

* **Ngôn ngữ:** [Kotlin](https://kotlinlang.org/)
* **Kiến trúc (Architecture):** MVVM (Model - View - ViewModel)
    * **Model:** Sử dụng `Data Class` để quản lý thuộc tính món ăn linh hoạt.
    * **ViewModel:** Quản lý trạng thái và logic tính toán độc lập với UI.
    * **View:** Chỉ thực hiện nhiệm vụ hiển thị và lắng nghe sự kiện.
* **Cập nhật giao diện (Reactive UI):** `LiveData` & `MutableLiveData` (Lifecycle-aware).
* **Giao diện (UI Components):**
    * `View Binding`: Tương tác UI an toàn, tránh NullPointerException.
    * `ConstraintLayout`: Xây dựng bố cục linh hoạt, responsive.
    * `ViewPager2` kết hợp `BottomNavigationView`: Điều hướng mượt mà giữa các Fragments.
    * `Material Components`: MaterialToolbar, ShapeableImageView, Buttons.

## 🚀 Hướng dẫn cài đặt (Getting Started)

Để chạy dự án này trên máy cục bộ của bạn, vui lòng thực hiện các bước sau:

1.  **Clone repository này:**
    ```bash
    git clone https://github.com/Liamm-shkerpear/Nanobyte_Training_DrinkFoodApp.git
    ```
2.  Mở **Android Studio**.
3.  Chọn **File > Open** và trỏ đến thư mục dự án vừa clone.
4.  Chờ Gradle đồng bộ hóa (Sync) xong.
5.  Kết nối máy ảo (Emulator) hoặc thiết bị thật và nhấn nút **Run** (hoặc `Shift + F10`).

## 📁 Cấu trúc thư mục (Folder Structure)

```text
app/src/main/java/com/example/drinkfoodapp/main/
│
├── model/
│   └── MenuItem.kt                 # Data class định nghĩa cấu trúc món ăn/đồ uống
│
├── viewmodel/
│   └── MainViewModel.kt            # Xử lý logic random, danh sách dữ liệu và LiveData
│
└── view/
    ├── activity/
    │   └── MainActivity.kt         # Activity chính điều hướng (ViewPager2 + BottomNav)
    │
    ├── fragment/
    │   └── ItemFragment.kt         # Fragment hiển thị nội dung chi tiết món ăn/đồ uống
    │
    └── adapter/
        └── ViewPagerAdapter.kt     # Adapter quản lý việc chuyển đổi giữa các Fragment
