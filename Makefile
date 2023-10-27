TARGET_DIR=./bin
TARGET_BACKUP_DIR=./bin/backup

BUILD_NUM=$$(cat ./build_num.txt)
BUILD_NUM_FILE=./build_num.txt
VERSION_NUM=$$(cat ./version.txt)

ADMIN_MODULE_NAME=real-estate-admin-web
ADMIN_MODULE_DIR=./real-estate-admin-web/build/libs
ADMIN_MODULE_PID=real-estate-admin-web.jar

service-api: config boot_jar move_jar

config:
	@if [ ! -d $(TARGET_DIR) ]; then mkdir $(TARGET_DIR); fi
	@if [ ! -d $(TARGET_BACKUP_DIR) ]; then mkdir $(TARGET_BACKUP_DIR); fi

build_num:
	@echo $$(($$(cat $(BUILD_NUM_FILE)) + 1 )) > $(BUILD_NUM_FILE)


boot_jar:
	./gradlew clean :$(ADMIN_MODULE_NAME):bootjar

move_jar:
	cp $(ADMIN_MODULE_DIR)/$(ADMIN_MODULE_PID) $(TARGET_DIR)/$(ADMIN_MODULE_PID)

clean:
	rm -rf $(TARGET_DIR)/*.jar


