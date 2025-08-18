# UET Library: Hệ thống quản lý thư viện
<p align="center">
  <img src="book.png" alt="" width="500">
</p>

UET Library - Hệ thống quản lý thư viện, được thiết kế để hỗ trợ việc quản lý thư viện cho Trường Đại học Công Nghệ. Hệ thống được xây dựng với giao diện tiện lợi, dễ dùng, đồng thời giúp quản thư quản lý kho thư viện cùng việc mượn sách của sinh viên.



# Chạy dự án bằng Maven và React

Nếu bạn đã cài sẵn Maven trên máy tính cảu mình, bạn có thể thực hiện theo các bước dưới đấy để cài đặt và chạy hệ thống:


Bước 1: Khởi động Docker Desktop

Bước 2: Tải xuống repo này chữa src code của hệ thống

Bước 3: Mở thư mục của bạn trong terminal và chạy câu lệnh sau:

```bash
   npm install --save-dev concurrently
   npm  run start
```

Bạn sẽ cần chờ một lúc để hệ thống khởi chạy.

Từ lần thứ 2 chạy chương trình bạn chỉ cần chạy lệnh

```bash
    npm run start
```

Bước 4: Khi khởi động hoàn tất, bạn đã có thể sử dụng ứng dụng.

    Mở http://localhost:3000/ để vào giao diện quản lý

# Các chức năng chính của hệ thống

- Thêm, xóa phiếu mượn sách của sinh viên
- Thêm, sửa, xóa một cuốn sách trong kho
- thêm, sửa, xóa một sinh viên trong hệ thống


## Chức năng cho người dùng

<center>
<img src="image/dashboardview.png">

Khi mở ứng dụng, giảng viên sẽ tìm và lựa chọn lớp học của mình để tạo ca điểm danh

</center>


# Cấu trúc hệ thống

| Thành phần                    | Công nghệ/thư viện        |
|-------------------------------|---------------------------|
| Backend                       | Java, Javalin             |
| Cơ sở dữ liệu                 | MySQL                     |
| Giao diện quản lý thư viện    | ReactJS-HTML-CSS          |




# Đội ngũ phát triển

| Họ tên             | Mã sinh viên | Vai trò                             |
|--------------------|--------------|-------------------------------------|
| Nguyễn Duy Hoàng   |     23020368 | Quản lý dự án                       |
| Hoàng Ngọc Nam     |     23020343 | Phát triển phần mềm                 |
| Vi Minh Hiển       |     23020363 | Phát triển giao diện                |
