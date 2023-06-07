import java.io.File;
import java.nio.file.*;

public class WatchDirectory {
    public static void main(String[] args) {
        run("E:\\Test");
    }

    public static void run(String path) {
        try {
            // Đường dẫn đến thư mục bạn muốn theo dõi
            Path directoryPath = Paths.get(path);

            // Tạo một WatchService
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // Đăng ký sự kiện theo dõi trên thư mục
            directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            // Vòng lặp để xử lý sự kiện
            while (true) {
                WatchKey key;
                try {
                    // Lắng nghe sự kiện từ WatchService
                    key = watchService.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                // Xử lý tất cả các sự kiện theo dõi trong WatchKey
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    // Xử lý sự kiện theo dõi tương ứng
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        System.out.println("Sự kiện OVERFLOW xảy ra. Có sự kiện bị bỏ qua.");
                        continue;
                    } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        // Sự kiện có tệp tin được tạo mới trong thư mục
                        System.out.println("Tệp tin mới được tạo: " + path + "\\" + event.context().toString());
                        File file = new File(path + "\\" + event.context().toString());
                        if (file.isDirectory()) {
                            Thread thread = new Thread(()-> {
                                run(path + "\\" + event.context().toString());
                            });
                            thread.start();
                        }
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        // Sự kiện có tệp tin được sửa đổi trong thư mục
                        System.out.println("Tệp tin được sửa đổi: " + event.context());
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        // Sự kiện có tệp tin bị xóa trong thư mục
                        System.out.println("Tệp tin bị xóa: " + event.context());
                    }
                }

                // Đặt WatchKey quay trở lại trạng thái sẵn sàng để tiếp tục theo dõi sự kiện
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
