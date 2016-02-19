package cn.chamatou.commons.data.filesystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.chamatou.commons.data.utils.CoderUtil;
import cn.chamatou.commons.data.utils.FileSystemUtils;
import cn.chamatou.commons.data.utils.IOUtils;

public class LocalFileStore implements FileStoreManager {
	private File path;

	public void setStorePath(String storePath) {
		path=new File(storePath);
		if (!path.exists()) {
			if (!path.mkdirs()) {
				throw new RuntimeException("LocalFileStore create dir fail.");
			}
		}
	}

	@Override
	public String store(String accountid,String fileName, byte[] bytes)throws IOException {
		String id = createId(accountid,fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(id);
			out.write(bytes, 0, bytes.length);
			out.flush();
			return id;
		} finally {
			IOUtils.close(out);
		}
	}
	private String createId(String accountid,String fileName) {
		String suffix = FileSystemUtils.getSuffix(fileName);
		if (suffix != null) {
			path = new File(path.getAbsolutePath() + "/"+accountid+"/" + suffix);
			if (!path.exists()) {
				path.mkdirs();
			}
		}
		String id = path.getAbsolutePath() + "/" + fileName;
		return id.replace("\\", "/");
	}

	@Override
	public String store(String accountid,File localFile) throws IOException{
		String id = createId(accountid,localFile.getName());
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(localFile);
			out = new FileOutputStream(id);
			IOUtils.copyFile(in, out);
			return id;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			IOUtils.close(out);
			IOUtils.close(in);
		}
		return null;
	}

	@Override
	public String store(String accountid,String fileName, InputStream in) throws IOException{
		String id = createId(accountid,fileName);
		FileOutputStream out=null;
		try {
			out = new FileOutputStream(id);
			IOUtils.copyFile(in, out);
			return id;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			IOUtils.close(out);
		}
		return null;
	}

	@Override
	public boolean delete(String id) throws IOException {
		return new File(id).delete();
	}

	@Override
	public InputStream load(String id) throws IOException {
		return new FileInputStream(id);
	}

	@Override
	public String get(String id)throws IOException {
		File file=new File(id);
		FileInputStream in=null;
		try{
			in=new FileInputStream(file);
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			IOUtils.copyFile(in, out);
			return CoderUtil.base64Encode(out.toByteArray());
		}finally{
			IOUtils.close(in);
		}
	}

	@Override
	public boolean isSupportHtmlAccess() {
		return false;
	}

	@Override
	public String toHtmlLoad(String id) {
		return "";
	}
}
