# -*- coding: <encoding utf-8> -*-
import argparse
import os
import shutil

'''
自动打包脚本
Usage:
    python make_apkxxx.py
Function:
    1.调用gradle打包命令自动打包；
    2.打包完自动备份。
 '''
####打包时注意修改以下几个参数#### START
# 项目根目录
root_dir = "E:/Privacy/BaseProject"
# 备份目录：每一个版本都会进行备份。
backup_dir = "F:/ProjectBackup/BaseProject"

# 桌面目录，每次打包后缓存一份到桌面，便于测试
desk_dir = "C:/Users/Administrator/Desktop"
# 是否要混淆资源, True -> 是， False ->不是
proguard_res = False


####打包时注意修改以上几个参数#### END


def clear_dir(dir):
    if not os.path.exists(dir):
        return
    files = os.listdir(dir)
    for file in files:
        if os.path.isfile(dir + '/' + file):
            os.remove(dir + '/' + file)
        else:
            clear_dir(dir + '/' + file)


def make_apk():
    cmd = '{}/gradlew {}'.format(root_dir, "resguardRelease" if proguard_res else "assembleRelease")
    print("打包命令 -> ", cmd)
    os.system(cmd)


def copy(src, des):
    print("copy file from \n {} \n to \n {} \n".format(src, des))
    shutil.copyfile(src, des)


def backup():
    print('Start backup ' + "=" * 20)
    dir = root_dir + "/app/build/outputs/apk/release/"
    apk_name = ""

    if os.path.exists(backup_dir) == False:
        os.mkdir(backup_dir)
    for file in os.listdir(dir):
        if file.endswith(".apk"):
            apk_name = file
            copy(dir + file, os.path.join(backup_dir, file))
            app_name = file.split("_")[3]
            version = file.split("_")[1]
            copy(dir + file, desk_dir + app_name + "_" + version + ".apk")
            copy(dir + file, os.path.join(desk_dir, app_name + "_" + version + ".apk"))

    mapping_dir = root_dir + "/app/build/outputs/mapping/release/"
    for file in os.listdir(mapping_dir):
        if file.endswith("mapping.txt"):
            copy(mapping_dir + file, os.path.join(backup_dir, apk_name.replace(".apk", "_mapping.txt")))
    print('Backup finished ' + "=" * 20)
    pass


def delete_cache():
    print('Start clear  ' + "=" * 20)
    if proguard_res:
        os.chdir(root_dir)
        os.system("{}/gradlew clean resguardDebug".format(root_dir))
    release_dir = root_dir + "/app/build/outputs/apk/release/"
    clear_dir(release_dir)
    print('Clean finished ' + "=" * 20)
    pass


if __name__ == '__main__':
    delete_cache()
    make_apk()
    backup()
pass
