package cn.chamatou.commons.data.utils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * 文件系统工具
 *
 */
public class FileSystemUtils {
	private static final MimetypesFileTypeMap ftm;
	/**
	 * 获取指定文件的mimeType
	 * @param file
	 * @return
	 */
	public static String getMimeType(File file){
		return ftm.getContentType(file);
	}
	/**
	 * 判断是否可写入指定大小的文件,可以写入返回true
	 * @param path
	 * @param size
	 * @return
	 */
	public static boolean canWrite(String path,long size){
		File file=new File(path);
		return file.exists()&&file.getFreeSpace()>size;
	}
	/**
	 * 判断指定路径是否可写入当前大小的文件
	 * @param path
	 * @param size
	 * @return
	 */
	public static boolean freeSpace(String path,long size){
		File file=new File(path);
		return file.getFreeSpace()>size;
	}
	/**
	 * 如果目录不存在,则创建目录
	 * @param path
	 * @throws IOException 
	 */
	public static void tryCreateDirectories(Path path) throws IOException{
		if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS)){//写文件目录不存在,创建写文件目录
			Files.createDirectories(path);
		}
	}
	
	public static void close(Closeable closeable){
		if(closeable!=null){
			try {
				closeable.close();
				closeable=null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 生成文件唯一标示符
	 * @param file
	 * @return 如果参数file不是一个文件,返回一个空串
	 */
	public static String generateFileId(File file){
		if(!file.isFile()||!file.exists())return "";
		//对文件全路径名称,文件mimetype,文件长度,文件最后修改时间进行字符相加后md5
		//如果文件小于10M,对整个文件进行MD5
		if(file.length()>10485760){
			String mimeType=getMimeType(file);
			String suffix=getSuffix(file);
			String last=String.valueOf(file.lastModified());
			String path=file.getAbsolutePath();
			String length=String.valueOf(file.length());
			return DigestUtils.sha512Hex(path+mimeType+length+suffix+last);			
		}else{
			FileInputStream in=null;
			try {
				in = new FileInputStream(file);
				return DigestUtils.md5Hex(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return "";
	}
	/**
	 * 获取操作系统临时文件夹
	 * @return
	 */
	public static File getTmp(){
		return new File(System.getProperty("java.io.tmpdir"));
	}
	public static File getUserDir(){
		return new File(System.getProperty("user.dir"));
	}
	/**
	 * 获取文件前缀,不包含.
	 * @param file
	 * @return
	 */
	public static String getPrefix(File file){
		return getPreFix(file.getName());
	}
	
	public static String getPreFix(String fileName){
		int index=fileName.lastIndexOf(".");
		if(index<0){
			return null;
		}
		return fileName.substring(0, index);
	}
	/**
	 * 获取文件后缀,不包含.
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName){
		int index=fileName.lastIndexOf(".");
		if(index<0){
			return null;
		}
		return fileName.substring(index+1,fileName.length());
	}
	/**
	 * 获取文件后缀名,不包含.
	 * @param file
	 * @return
	 */
	public static String getSuffix(File file){
		return getSuffix(file.getName());
	}
	static{
		ftm=new MimetypesFileTypeMap();
		ftm.addMimeTypes("application/vnd.gmx gmx");
		ftm.addMimeTypes("text/calendar ifb");
		ftm.addMimeTypes("application/vnd.ezpix-package ez3");
		ftm.addMimeTypes("application/vnd.dece.zip uvz");
		ftm.addMimeTypes("application/cdmi-queue cdmiq");
		ftm.addMimeTypes("application/scvp-vp-request spq");
		ftm.addMimeTypes("application/x-iso9660-image iso");
		ftm.addMimeTypes("application/prs.cww cww");
		ftm.addMimeTypes("text/vnd.curl.mcurl mcurl");
		ftm.addMimeTypes("text/vnd.sun.j2me.app-descriptor jad");
		ftm.addMimeTypes("video/x-f4v f4v");
		ftm.addMimeTypes("application/vnd.groove-help ghf");
		ftm.addMimeTypes("application/relax-ng-compact-syntax rnc");
		ftm.addMimeTypes("video/vnd.fvt fvt");
		ftm.addMimeTypes("application/vnd.kde.kword kwt");
		ftm.addMimeTypes("application/vnd.zul zirz");
		ftm.addMimeTypes("application/dssc+xml xdssc");
		ftm.addMimeTypes("application/x-msterminal trm");
		ftm.addMimeTypes("text/x-setext etx");
		ftm.addMimeTypes("application/x-dvi dvi");
		ftm.addMimeTypes("text/troff tr");
		ftm.addMimeTypes("application/vnd.ms-excel.template.macroenabled.12 xltm");
		ftm.addMimeTypes("application/vnd.powerbuilder6 pbd");
		ftm.addMimeTypes("application/vnd.fujitsu.oasys oas");
		ftm.addMimeTypes("application/tei+xml teicorpus");
		ftm.addMimeTypes("video/x-ms-wmx wmx");
		ftm.addMimeTypes("application/vnd.sun.xml.writer sxw");
		ftm.addMimeTypes("video/x-ms-wmv wmv");
		ftm.addMimeTypes("application/vnd.dvb.ait ait");
		ftm.addMimeTypes("audio/flac flac");
		ftm.addMimeTypes("application/ipfix ipfix");
		ftm.addMimeTypes("video/vnd.dece.hd uvvh");
		ftm.addMimeTypes("application/vnd.wordperfect wpd");
		ftm.addMimeTypes("text/cache-manifest appcache");
		ftm.addMimeTypes("application/vnd.dece.ttml+xml uvvt");
		ftm.addMimeTypes("application/x-tex-tfm tfm");
		ftm.addMimeTypes("application/wspolicy+xml wspolicy");
		ftm.addMimeTypes("application/vnd.sus-calendar susp");
		ftm.addMimeTypes("application/vnd.syncml+xml xsm");
		ftm.addMimeTypes("application/vnd.ms-project mpt");
		ftm.addMimeTypes("application/vnd.framemaker maker");
		ftm.addMimeTypes("application/x-mswrite wri");
		ftm.addMimeTypes("application/vnd.wolfram.player nbp");
		ftm.addMimeTypes("application/x-msmetafile wmz");
		ftm.addMimeTypes("application/vnd.dece.unspecified uvx");
		ftm.addMimeTypes("application/vnd.contact.cmsg cdbcmsg");
		ftm.addMimeTypes("application/x-envoy evy");
		ftm.addMimeTypes("application/vnd.trid.tpt tpt");
		ftm.addMimeTypes("application/vnd.shana.informed.formdata ifm");
		ftm.addMimeTypes("application/x-bittorrent torrent");
		ftm.addMimeTypes("application/vnd.chipnuts.karaoke-mmd mmd");
		ftm.addMimeTypes("application/srgs gram");
		ftm.addMimeTypes("application/vnd.uoml+xml uoml");
		ftm.addMimeTypes("application/pkixcmp pki");
		ftm.addMimeTypes("application/vnd.visio vsw");
		ftm.addMimeTypes("application/vnd.kodak-descriptor sse");
		ftm.addMimeTypes("application/ogg ogx");
		ftm.addMimeTypes("application/vnd.picsel efif");
		ftm.addMimeTypes("image/x-3ds 3ds");
		ftm.addMimeTypes("image/vnd.wap.wbmp wbmp");
		ftm.addMimeTypes("application/atomsvc+xml atomsvc");
		ftm.addMimeTypes("application/vnd.shana.informed.formtemplate itp");
		ftm.addMimeTypes("application/vnd.symbian.install sisx");
		ftm.addMimeTypes("text/vnd.wap.wmlscript wmls");
		ftm.addMimeTypes("application/vnd.mcd mcd");
		ftm.addMimeTypes("application/vnd.isac.fcs fcs");
		ftm.addMimeTypes("application/postscript ps");
		ftm.addMimeTypes("image/x-portable-pixmap ppm");
		ftm.addMimeTypes("application/x-authorware-bin x32");
		ftm.addMimeTypes("application/thraud+xml tfi");
		ftm.addMimeTypes("application/vnd.syncml.dm+xml xdm");
		ftm.addMimeTypes("application/vnd.hal+xml hal");
		ftm.addMimeTypes("video/x-ms-asf asx");
		ftm.addMimeTypes("application/patch-ops-error+xml xer");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.presentation-template otp");
		ftm.addMimeTypes("application/vnd.solent.sdkm+xml sdkm");
		ftm.addMimeTypes("application/ssml+xml ssml");
		ftm.addMimeTypes("application/vnd.kidspiration kia");
		ftm.addMimeTypes("application/rpki-manifest mft");
		ftm.addMimeTypes("application/vnd.yamaha.hv-dic hvd");
		ftm.addMimeTypes("application/vnd.lotus-freelance pre");
		ftm.addMimeTypes("application/vnd.crick.clicker.template clkt");
		ftm.addMimeTypes("application/x-rar-compressed rar");
		ftm.addMimeTypes("application/shf+xml shf");
		ftm.addMimeTypes("application/vnd.syncml.dm+wbxml bdm");
		ftm.addMimeTypes("application/vnd.yamaha.openscoreformat osf");
		ftm.addMimeTypes("application/x-doom wad");
		ftm.addMimeTypes("application/javascript js");
		ftm.addMimeTypes("application/vnd.cluetrust.cartomobile-config c11amc");
		ftm.addMimeTypes("audio/x-aiff aiff");
		ftm.addMimeTypes("application/vnd.hp-pcl pcl");
		ftm.addMimeTypes("model/vnd.gtw gtw");
		ftm.addMimeTypes("application/metalink+xml metalink");
		ftm.addMimeTypes("image/vnd.fujixerox.edmics-mmr mmr");
		ftm.addMimeTypes("audio/mpeg mpga");
		ftm.addMimeTypes("application/x-blorb blorb");
		ftm.addMimeTypes("application/vnd.rig.cryptonote cryptonote");
		ftm.addMimeTypes("application/vnd.kenameaapp htke");
		ftm.addMimeTypes("application/vnd.nokia.radio-preset rpst");
		ftm.addMimeTypes("application/vnd.hydrostatix.sof-data sfd-hdstx");
		ftm.addMimeTypes("application/x-font-snf snf");
		ftm.addMimeTypes("text/x-asm s");
		ftm.addMimeTypes("chemical/x-cml cml");
		ftm.addMimeTypes("application/vnd.sun.xml.draw sxd");
		ftm.addMimeTypes("application/octet-stream so");
		ftm.addMimeTypes("application/vnd.webturbo wtb");
		ftm.addMimeTypes("image/vnd.xiff xif");
		ftm.addMimeTypes("application/vnd.enliven nml");
		ftm.addMimeTypes("application/pgp-signature sig");
		ftm.addMimeTypes("application/x-pkcs7-certificates spc");
		ftm.addMimeTypes("application/rpki-ghostbusters gbr");
		ftm.addMimeTypes("application/vnd.ms-word.document.macroenabled.12 docm");
		ftm.addMimeTypes("application/cdmi-object cdmio");
		ftm.addMimeTypes("application/x-wais-source src");
		ftm.addMimeTypes("application/vnd.ms-artgalry cil");
		ftm.addMimeTypes("audio/x-pn-realaudio ram");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.spreadsheet-template ots");
		ftm.addMimeTypes("text/vnd.in3d.spot spot");
		ftm.addMimeTypes("application/vnd.ms-lrm lrm");
		ftm.addMimeTypes("application/java-serialized-object ser");
		ftm.addMimeTypes("application/x-msaccess mdb");
		ftm.addMimeTypes("application/vnd.stardivision.draw sda");
		ftm.addMimeTypes("video/vnd.dece.mobile uvvm");
		ftm.addMimeTypes("application/vnd.joost.joda-archive joda");
		ftm.addMimeTypes("application/docbook+xml dbk");
		ftm.addMimeTypes("video/vnd.dvb.file dvb");
		ftm.addMimeTypes("audio/annodex axa");
		ftm.addMimeTypes("application/davmount+xml davmount");
		ftm.addMimeTypes("application/vnd.igloader igl");
		ftm.addMimeTypes("chemical/x-cmdf cmdf");
		ftm.addMimeTypes("application/vnd.adobe.xfdf xfdf");
		ftm.addMimeTypes("image/x-portable-bitmap pbm");
		ftm.addMimeTypes("application/zip zip");
		ftm.addMimeTypes("application/x-pkcs12 pfx");
		ftm.addMimeTypes("text/x-component htc");
		ftm.addMimeTypes("application/andrew-inset ez");
		ftm.addMimeTypes("application/xop+xml xop");
		ftm.addMimeTypes("image/ktx ktx");
		ftm.addMimeTypes("application/x-msschedule scd");
		ftm.addMimeTypes("application/x-mobipocket-ebook prc");
		ftm.addMimeTypes("application/x-java-jnlp-file jnlp");
		ftm.addMimeTypes("application/vnd.epson.salt slt");
		ftm.addMimeTypes("application/vnd.anser-web-funds-transfer-initiation fti");
		ftm.addMimeTypes("application/vnd.chemdraw+xml cdxml");
		ftm.addMimeTypes("text/x-opml opml");
		ftm.addMimeTypes("application/vnd.kde.kpresenter kpt");
		ftm.addMimeTypes("application/xspf+xml xspf");
		ftm.addMimeTypes("application/vnd.publishare-delta-tree qps");
		ftm.addMimeTypes("application/pkcs7-signature p7s");
		ftm.addMimeTypes("audio/x-pn-realaudio-plugin rmp");
		ftm.addMimeTypes("application/x-install-instructions install");
		ftm.addMimeTypes("application/vnd.irepository.package+xml irp");
		ftm.addMimeTypes("image/prs.btif btif");
		ftm.addMimeTypes("application/mac-binhex40 hqx");
		ftm.addMimeTypes("audio/x-ms-wma wma");
		ftm.addMimeTypes("application/vnd.curl.car car");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.wordprocessingml.document docx");
		ftm.addMimeTypes("application/mp4 mp4s");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.presentation odp");
		ftm.addMimeTypes("model/x3d+vrml x3dvz");
		ftm.addMimeTypes("application/vnd.insors.igm igm");
		ftm.addMimeTypes("application/vnd.spotfire.dxp dxp");
		ftm.addMimeTypes("application/vnd.groove-injector grv");
		ftm.addMimeTypes("application/ecmascript ecma");
		ftm.addMimeTypes("image/x-cmx cmx");
		ftm.addMimeTypes("application/x-freearc arc");
		ftm.addMimeTypes("application/rdf+xml rdf");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.text-web oth");
		ftm.addMimeTypes("application/vnd.tao.intent-module-archive tao");
		ftm.addMimeTypes("application/vnd.noblenet-directory nnd");
		ftm.addMimeTypes("application/vnd.amazon.ebook azw");
		ftm.addMimeTypes("text/x-nfo nfo");
		ftm.addMimeTypes("application/vnd.wap.wbxml wbxml");
		ftm.addMimeTypes("application/vnd.mobius.mqy mqy");
		ftm.addMimeTypes("application/vnd.blueice.multipass mpm");
		ftm.addMimeTypes("text/vcard vcard");
		ftm.addMimeTypes("application/vnd.mfer mwf");
		ftm.addMimeTypes("audio/x-mpegurl m3u");
		ftm.addMimeTypes("application/vnd.pg.format str");
		ftm.addMimeTypes("application/vnd.fsc.weblaunch fsc");
		ftm.addMimeTypes("application/yang yang");
		ftm.addMimeTypes("application/x-dtbncx+xml ncx");
		ftm.addMimeTypes("application/x-debian-package udeb");
		ftm.addMimeTypes("application/vnd.pvi.ptid1 ptid");
		ftm.addMimeTypes("text/css css");
		ftm.addMimeTypes("application/vnd.zzazz.deck+xml zaz");
		ftm.addMimeTypes("audio/vnd.digital-winds eol");
		ftm.addMimeTypes("application/x-lzh-compressed lzh");
		ftm.addMimeTypes("text/csv csv");
		ftm.addMimeTypes("chemical/x-cdx cdx");
		ftm.addMimeTypes("audio/vnd.dece.audio uvva");
		ftm.addMimeTypes("application/x-subrip srt");
		ftm.addMimeTypes("application/vnd.data-vision.rdz rdz");
		ftm.addMimeTypes("application/vnd.kinar knp");
		ftm.addMimeTypes("image/x-cmu-raster ras");
		ftm.addMimeTypes("application/x-gtar gtar");
		ftm.addMimeTypes("application/vnd.groove-tool-template tpl");
		ftm.addMimeTypes("application/vnd.quark.quarkxpress qxt");
		ftm.addMimeTypes("application/x-dtbook+xml dtb");
		ftm.addMimeTypes("text/turtle ttl");
		ftm.addMimeTypes("audio/vnd.ms-playready.media.pya pya");
		ftm.addMimeTypes("application/vnd.lotus-wordpro lwp");
		ftm.addMimeTypes("image/vnd.ms-modi mdi");
		ftm.addMimeTypes("application/set-registration-initiation setreg");
		ftm.addMimeTypes("audio/xm xm");
		ftm.addMimeTypes("application/vnd.3m.post-it-notes pwn");
		ftm.addMimeTypes("application/vnd.intu.qbo qbo");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.wordprocessingml.template dotx");
		ftm.addMimeTypes("application/pkcs8 p8");
		ftm.addMimeTypes("video/h263 h263");
		ftm.addMimeTypes("video/vnd.uvvu.mp4 uvvu");
		ftm.addMimeTypes("video/h261 h261");
		ftm.addMimeTypes("application/vnd.oma.dd2+xml dd2");
		ftm.addMimeTypes("application/x-mie mie");
		ftm.addMimeTypes("video/mpeg2 mpv2");
		ftm.addMimeTypes("application/x-mif mif");
		ftm.addMimeTypes("video/h264 h264");
		ftm.addMimeTypes("application/x-bzip bz");
		ftm.addMimeTypes("application/x-shockwave-flash swf");
		ftm.addMimeTypes("application/vnd.grafeq gqs");
		ftm.addMimeTypes("audio/ogg spx");
		ftm.addMimeTypes("application/vnd.ms-fontobject eot");
		ftm.addMimeTypes("application/vnd.tcpdump.pcap pcap");
		ftm.addMimeTypes("application/vnd.ms-pki.seccat cat");
		ftm.addMimeTypes("application/vnd.epson.ssf ssf");
		ftm.addMimeTypes("application/vnd.nitf ntf");
		ftm.addMimeTypes("application/pkcs7-mime p7m");
		ftm.addMimeTypes("image/bmp dib");
		ftm.addMimeTypes("application/vnd.kde.kivio flw");
		ftm.addMimeTypes("audio/vnd.nuera.ecelp7470 ecelp7470");
		ftm.addMimeTypes("application/vnd.seemail see");
		ftm.addMimeTypes("application/vnd.groove-vcard vcg");
		ftm.addMimeTypes("chemical/x-cif cif");
		ftm.addMimeTypes("image/vnd.djvu djvu");
		ftm.addMimeTypes("text/prs.lines.tag dsc");
		ftm.addMimeTypes("application/vnd.pocketlearn plf");
		ftm.addMimeTypes("application/xhtml+xml xhtml");
		ftm.addMimeTypes("application/x-cdf cdf");
		ftm.addMimeTypes("application/xproc+xml xpl");
		ftm.addMimeTypes("application/vnd.fuzzysheet fzs");
		ftm.addMimeTypes("application/vnd.ahead.space ahead");
		ftm.addMimeTypes("application/vnd.ms-wpl wpl");
		ftm.addMimeTypes("image/png png");
		ftm.addMimeTypes("audio/vnd.dra dra");
		ftm.addMimeTypes("application/mxf mxf");
		ftm.addMimeTypes("application/vnd.ms-officetheme thmx");
		ftm.addMimeTypes("text/x-uuencode uu");
		ftm.addMimeTypes("application/vnd.fujitsu.oasys3 oa3");
		ftm.addMimeTypes("model/x3d+binary x3dbz");
		ftm.addMimeTypes("application/vnd.ms-ims ims");
		ftm.addMimeTypes("application/vnd.epson.esf esf");
		ftm.addMimeTypes("application/vnd.fujitsu.oasys2 oa2");
		ftm.addMimeTypes("application/vnd.lotus-approach apr");
		ftm.addMimeTypes("application/pics-rules prf");
		ftm.addMimeTypes("image/x-tga tga");
		ftm.addMimeTypes("text/x-pascal pas");
		ftm.addMimeTypes("model/iges igs");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.chart odc");
		ftm.addMimeTypes("image/x-xbitmap xbm");
		ftm.addMimeTypes("application/vnd.geoplan g2w");
		ftm.addMimeTypes("application/vnd.kde.kspread ksp");
		ftm.addMimeTypes("application/vnd.immervision-ivp ivp");
		ftm.addMimeTypes("application/rsd+xml rsd");
		ftm.addMimeTypes("text/sgml sgml");
		ftm.addMimeTypes("application/x-cbr cbz");
		ftm.addMimeTypes("application/vnd.curl.pcurl pcurl");
		ftm.addMimeTypes("application/vnd.immervision-ivu ivu");
		ftm.addMimeTypes("application/vnd.ecowin.chart mag");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.presentationml.presentation pptx");
		ftm.addMimeTypes("image/vnd.net-fpx npx");
		ftm.addMimeTypes("application/vnd.intu.qfx qfx");
		ftm.addMimeTypes("application/vnd.jam jam");
		ftm.addMimeTypes("application/vnd.lotus-organizer org");
		ftm.addMimeTypes("application/mp21 mp21");
		ftm.addMimeTypes("application/mathematica nb");
		ftm.addMimeTypes("audio/vnd.dts dts");
		ftm.addMimeTypes("application/vnd.ibm.minipay mpy");
		ftm.addMimeTypes("application/vnd.yamaha.hv-voice hvp");
		ftm.addMimeTypes("application/set-payment-initiation setpay");
		ftm.addMimeTypes("application/vnd.apple.mpegurl m3u8");
		ftm.addMimeTypes("application/vnd.mynfc taglet");
		ftm.addMimeTypes("application/vnd.dece.data uvvf");
		ftm.addMimeTypes("application/vnd.fujixerox.ddd ddd");
		ftm.addMimeTypes("application/vnd.uiq.theme utz");
		ftm.addMimeTypes("application/vnd.ms-powerpoint.slideshow.macroenabled.12 ppsm");
		ftm.addMimeTypes("application/x-font-linux-psf psf");
		ftm.addMimeTypes("application/vnd.mfmp mfm");
		ftm.addMimeTypes("application/rpki-roa roa");
		ftm.addMimeTypes("audio/basic ulw");
		ftm.addMimeTypes("video/mpeg mpg");
		ftm.addMimeTypes("application/vnd.mophun.application mpn");
		ftm.addMimeTypes("audio/vnd.nuera.ecelp9600 ecelp9600");
		ftm.addMimeTypes("application/vnd.stepmania.stepchart sm");
		ftm.addMimeTypes("image/x-mrsid-image sid");
		ftm.addMimeTypes("application/vnd.fujitsu.oasysgp fg5");
		ftm.addMimeTypes("application/x-bcpio bcpio");
		ftm.addMimeTypes("video/x-smv smv");
		ftm.addMimeTypes("video/x-mng mng");
		ftm.addMimeTypes("application/vnd.llamagraphics.life-balance.exchange+xml lbe");
		ftm.addMimeTypes("application/scvp-vp-response spp");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.image-template oti");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.chart-template otc");
		ftm.addMimeTypes("image/vnd.fpx fpx");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.formula odf");
		ftm.addMimeTypes("application/atom+xml atom");
		ftm.addMimeTypes("application/vnd.cosmocaller cmc");
		ftm.addMimeTypes("text/x-java-source java");
		ftm.addMimeTypes("message/rfc822 mime");
		ftm.addMimeTypes("x-conference/x-cooltalk ice");
		ftm.addMimeTypes("text/n3 n3");
		ftm.addMimeTypes("application/vnd.noblenet-sealer nns");
		ftm.addMimeTypes("application/xml xsl");
		ftm.addMimeTypes("application/vnd.medcalcdata mc1");
		ftm.addMimeTypes("text/vnd.fmi.flexstor flx");
		ftm.addMimeTypes("application/vnd.simtech-mindmapper twds");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.graphics-template otg");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.image odi");
		ftm.addMimeTypes("application/vnd.ms-cab-compressed cab");
		ftm.addMimeTypes("application/vnd.dynageo geo");
		ftm.addMimeTypes("application/vnd.wqd wqd");
		ftm.addMimeTypes("application/pkix-attr-cert ac");
		ftm.addMimeTypes("application/vnd.openofficeorg.extension oxt");
		ftm.addMimeTypes("application/vnd.visionary vis");
		ftm.addMimeTypes("application/vnd.pg.osasli ei6");
		ftm.addMimeTypes("text/uri-list urls");
		ftm.addMimeTypes("text/vnd.curl.dcurl dcurl");
		ftm.addMimeTypes("application/ssdl+xml ssdl");
		ftm.addMimeTypes("application/vnd.handheld-entertainment+xml zmm");
		ftm.addMimeTypes("application/vnd.ms-pki.stl stl");
		ftm.addMimeTypes("application/vnd.groove-identity-message gim");
		ftm.addMimeTypes("video/x-ms-wvx wvx");
		ftm.addMimeTypes("application/dssc+der dssc");
		ftm.addMimeTypes("application/x-sv4cpio sv4cpio");
		ftm.addMimeTypes("application/vnd.eszigno3+xml et3");
		ftm.addMimeTypes("application/vnd.umajin umj");
		ftm.addMimeTypes("application/x-cfs-compressed cfs");
		ftm.addMimeTypes("application/pdf pdf");
		ftm.addMimeTypes("application/vnd.groove-tool-message gtm");
		ftm.addMimeTypes("application/vnd.mediastation.cdkey cdkey");
		ftm.addMimeTypes("application/widget wgt");
		ftm.addMimeTypes("application/gml+xml gml");
		ftm.addMimeTypes("application/x-bzip2 bz2");
		ftm.addMimeTypes("application/vnd.yamaha.smaf-phrase spf");
		ftm.addMimeTypes("application/vnd.sun.xml.calc sxc");
		ftm.addMimeTypes("audio/x-matroska mka");
		ftm.addMimeTypes("video/webm webm");
		ftm.addMimeTypes("application/x-stuffitx sitx");
		ftm.addMimeTypes("text/x-vcalendar vcs");
		ftm.addMimeTypes("application/x-director w3d");
		ftm.addMimeTypes("application/vnd.3gpp.pic-bw-large plb");
		ftm.addMimeTypes("application/x-msmediaview mvb");
		ftm.addMimeTypes("application/vnd.olpc-sugar xo");
		ftm.addMimeTypes("text/html html");
		ftm.addMimeTypes("application/vnd.adobe.fxp fxpl");
		ftm.addMimeTypes("application/pkix-cert cer");
		ftm.addMimeTypes("image/vnd.adobe.photoshop psd");
		ftm.addMimeTypes("application/vnd.amiga.ami ami");
		ftm.addMimeTypes("application/x-dgc-compressed dgc");
		ftm.addMimeTypes("application/vnd.sun.xml.calc.template stc");
		ftm.addMimeTypes("video/jpeg jpgv");
		ftm.addMimeTypes("application/vnd.commonspace csp");
		ftm.addMimeTypes("image/vnd.fujixerox.edmics-rlc rlc");
		ftm.addMimeTypes("application/vnd.geometry-explorer gre");
		ftm.addMimeTypes("model/x3d+xml x3dz");
		ftm.addMimeTypes("application/vnd.smaf mmf");
		ftm.addMimeTypes("application/x-msdownload msi");
		ftm.addMimeTypes("application/vnd.audiograph aep");
		ftm.addMimeTypes("application/x-xpinstall xpi");
		ftm.addMimeTypes("application/vnd.kde.kchart chrt");
		ftm.addMimeTypes("application/vnd.anser-web-certificate-issue-initiation cii");
		ftm.addMimeTypes("application/resource-lists+xml rl");
		ftm.addMimeTypes("application/vnd.route66.link66+xml link66");
		ftm.addMimeTypes("application/vnd.mobius.mbk mbk");
		ftm.addMimeTypes("application/x-msmoney mny");
		ftm.addMimeTypes("application/yin+xml yin");
		ftm.addMimeTypes("application/hyperstudio stk");
		ftm.addMimeTypes("application/vnd.ufdl ufdl");
		ftm.addMimeTypes("application/vnd.fdsn.mseed mseed");
		ftm.addMimeTypes("application/vnd.fluxtime.clip ftc");
		ftm.addMimeTypes("application/vnd.dart dart");
		ftm.addMimeTypes("application/x-abiword abw");
		ftm.addMimeTypes("application/vnd.apple.installer+xml mpkg");
		ftm.addMimeTypes("application/vnd.ibm.modcap listafp");
		ftm.addMimeTypes("application/atomcat+xml atomcat");
		ftm.addMimeTypes("audio/vnd.nuera.ecelp4800 ecelp4800");
		ftm.addMimeTypes("application/vnd.smart.teacher teacher");
		ftm.addMimeTypes("application/mods+xml mods");
		ftm.addMimeTypes("application/x-csh csh");
		ftm.addMimeTypes("application/vnd.hp-hps hps");
		ftm.addMimeTypes("application/vnd.vcx vcx");
		ftm.addMimeTypes("application/reginfo+xml rif");
		ftm.addMimeTypes("text/vnd.in3d.3dml 3dml");
		ftm.addMimeTypes("application/x-compress Z");
		ftm.addMimeTypes("application/x-gca-compressed gca");
		ftm.addMimeTypes("application/vnd.ms-xpsdocument xps");
		ftm.addMimeTypes("application/vnd.iccprofile icm");
		ftm.addMimeTypes("application/vnd.xara xar");
		ftm.addMimeTypes("application/rtf rtf");
		ftm.addMimeTypes("application/vnd.mozilla.xul+xml xul");
		ftm.addMimeTypes("application/pskc+xml pskcxml");
		ftm.addMimeTypes("chemical/x-xyz xyz");
		ftm.addMimeTypes("application/x-tgif obj");
		ftm.addMimeTypes("application/vnd.rn-realmedia-vbr rmvb");
		ftm.addMimeTypes("audio/adpcm adp");
		ftm.addMimeTypes("application/pgp-encrypted pgp");
		ftm.addMimeTypes("application/vnd.fdsn.seed seed");
		ftm.addMimeTypes("application/vnd.bmi bmi");
		ftm.addMimeTypes("video/mj2 mjp2");
		ftm.addMimeTypes("application/oxps oxps");
		ftm.addMimeTypes("video/x-msvideo avi");
		ftm.addMimeTypes("application/vnd.jisp jisp");
		ftm.addMimeTypes("application/vnd.lotus-screencam scm");
		ftm.addMimeTypes("application/vnd.ms-powerpoint.presentation.macroenabled.12 pptm");
		ftm.addMimeTypes("application/vnd.ms-excel.sheet.macroenabled.12 xlsm");
		ftm.addMimeTypes("model/mesh silo");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.spreadsheetml.template xltx");
		ftm.addMimeTypes("text/richtext rtx");
		ftm.addMimeTypes("application/vnd.stardivision.impress sdd");
		ftm.addMimeTypes("video/3gpp 3gp");
		ftm.addMimeTypes("application/xenc+xml xenc");
		ftm.addMimeTypes("application/vnd.frogans.ltf ltf");
		ftm.addMimeTypes("application/vnd.cluetrust.cartomobile-config-pkg c11amz");
		ftm.addMimeTypes("video/quicktime qt");
		ftm.addMimeTypes("audio/mp4 mp4a");
		ftm.addMimeTypes("application/xml-dtd dtd");
		ftm.addMimeTypes("application/vnd.nokia.radio-presets rpss");
		ftm.addMimeTypes("application/vnd.mseq mseq");
		ftm.addMimeTypes("application/x-xz xz");
		ftm.addMimeTypes("application/vnd.kde.kontour kon");
		ftm.addMimeTypes("application/vnd.ms-powerpoint.slide.macroenabled.12 sldm");
		ftm.addMimeTypes("application/x-chess-pgn pgn");
		ftm.addMimeTypes("application/winhlp hlp");
		ftm.addMimeTypes("application/vnd.antix.game-component atx");
		ftm.addMimeTypes("application/x-font-ttf ttf");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.formula-template odft");
		ftm.addMimeTypes("application/x-sql sql");
		ftm.addMimeTypes("application/x-sh sh");
		ftm.addMimeTypes("application/x-font-otf otf");
		ftm.addMimeTypes("application/vnd.ds-keypoint kpxx");
		ftm.addMimeTypes("audio/x-aac aac");
		ftm.addMimeTypes("application/x-netcdf nc");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.presentationml.slide sldx");
		ftm.addMimeTypes("application/vnd.sun.xml.impress.template sti");
		ftm.addMimeTypes("video/x-matroska mkv");
		ftm.addMimeTypes("application/vnd.svd svd");
		ftm.addMimeTypes("text/vnd.fly fly");
		ftm.addMimeTypes("application/vnd.nokia.n-gage.data ngdat");
		ftm.addMimeTypes("application/vnd.yamaha.smaf-audio saf");
		ftm.addMimeTypes("application/x-gnumeric gnumeric");
		ftm.addMimeTypes("application/vnd.tmobile-livetv tmo");
		ftm.addMimeTypes("image/x-macpaint pnt");
		ftm.addMimeTypes("application/x-ms-shortcut lnk");
		ftm.addMimeTypes("application/vnd.yellowriver-custom-menu cmp");
		ftm.addMimeTypes("text/x-vcard vcf");
		ftm.addMimeTypes("image/x-xpixmap xpm");
		ftm.addMimeTypes("application/vnd.geonext gxt");
		ftm.addMimeTypes("application/x-conference nsc");
		ftm.addMimeTypes("application/vnd.dreamfactory dfac");
		ftm.addMimeTypes("image/vnd.fst fst");
		ftm.addMimeTypes("application/vnd.mobius.dis dis");
		ftm.addMimeTypes("application/vnd.adobe.formscentral.fcdt fcdt");
		ftm.addMimeTypes("application/x-tads gam");
		ftm.addMimeTypes("application/x-research-info-systems ris");
		ftm.addMimeTypes("application/x-ms-xbap xbap");
		ftm.addMimeTypes("application/sru+xml sru");
		ftm.addMimeTypes("model/vnd.dwf dwf");
		ftm.addMimeTypes("text/vnd.wap.wml wml");
		ftm.addMimeTypes("audio/x-scpls pls");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.text odt");
		ftm.addMimeTypes("audio/s3m s3m");
		ftm.addMimeTypes("application/vnd.ezpix-album ez2");
		ftm.addMimeTypes("application/vnd.triscape.mxs mxs");
		ftm.addMimeTypes("image/x-pcx pcx");
		ftm.addMimeTypes("application/vnd.3gpp.pic-bw-small psb");
		ftm.addMimeTypes("application/vnd.novadigm.ext ext");
		ftm.addMimeTypes("application/vnd.las.las+xml lasxml");
		ftm.addMimeTypes("image/sgi sgi");
		ftm.addMimeTypes("text/vnd.curl.scurl scurl");
		ftm.addMimeTypes("application/x-silverlight-app xap");
		ftm.addMimeTypes("application/x-zmachine z8");
		ftm.addMimeTypes("application/resource-lists-diff+xml rld");
		ftm.addMimeTypes("application/vnd.ctc-posml pml");
		ftm.addMimeTypes("application/vnd.sun.xml.draw.template std");
		ftm.addMimeTypes("application/ccxml+xml ccxml");
		ftm.addMimeTypes("application/annodex anx");
		ftm.addMimeTypes("application/x-font-ghostscript gsf");
		ftm.addMimeTypes("application/vnd.claymore cla");
		ftm.addMimeTypes("application/vnd.acucorp atc");
		ftm.addMimeTypes("application/vnd.wt.stf stf");
		ftm.addMimeTypes("application/vnd.ibm.rights-management irm");
		ftm.addMimeTypes("application/vnd.hp-hpid hpid");
		ftm.addMimeTypes("application/vnd.dpgraph dpg");
		ftm.addMimeTypes("application/vnd.google-earth.kml+xml kml");
		ftm.addMimeTypes("video/vnd.dece.video uvvv");
		ftm.addMimeTypes("application/cu-seeme cu");
		ftm.addMimeTypes("application/vnd.criticaltools.wbs+xml wbs");
		ftm.addMimeTypes("application/vnd.kde.kformula kfo");
		ftm.addMimeTypes("application/x-authorware-seg aas");
		ftm.addMimeTypes("application/vnd.cups-ppd ppd");
		ftm.addMimeTypes("model/vnd.mts mts");
		ftm.addMimeTypes("application/x-x509-ca-cert der");
		ftm.addMimeTypes("application/x-ms-wmd wmd");
		ftm.addMimeTypes("application/vnd.musician mus");
		ftm.addMimeTypes("application/x-xfig fig");
		ftm.addMimeTypes("application/vnd.mophun.certificate mpc");
		ftm.addMimeTypes("application/x-7z-compressed 7z");
		ftm.addMimeTypes("application/omdoc+xml omdoc");
		ftm.addMimeTypes("application/voicexml+xml vxml");
		ftm.addMimeTypes("application/vnd.noblenet-web nnw");
		ftm.addMimeTypes("model/vrml wrl");
		ftm.addMimeTypes("application/mets+xml mets");
		ftm.addMimeTypes("model/vnd.vtu vtu");
		ftm.addMimeTypes("application/vnd.hp-hpgl hpgl");
		ftm.addMimeTypes("application/marcxml+xml mrcx");
		ftm.addMimeTypes("application/vnd.fujitsu.oasysprs bh2");
		ftm.addMimeTypes("image/x-rgb rgb");
		ftm.addMimeTypes("application/vnd.hhe.lesson-player les");
		ftm.addMimeTypes("application/vnd.koan skt");
		ftm.addMimeTypes("application/vnd.hp-jlyt jlt");
		ftm.addMimeTypes("application/x-pkcs7-certreqresp p7r");
		ftm.addMimeTypes("application/vnd.yamaha.hv-script hvs");
		ftm.addMimeTypes("application/vnd.recordare.musicxml mxl");
		ftm.addMimeTypes("application/metalink4+xml meta4");
		ftm.addMimeTypes("application/vnd.macports.portpkg portpkg");
		ftm.addMimeTypes("application/xv+xml xvml");
		ftm.addMimeTypes("application/vnd.ms-works wps");
		ftm.addMimeTypes("text/vnd.graphviz gv");
		ftm.addMimeTypes("application/vnd.dolby.mlp mlp");
		ftm.addMimeTypes("application/oebps-package+xml opf");
		ftm.addMimeTypes("application/x-eva eva");
		ftm.addMimeTypes("audio/silk sil");
		ftm.addMimeTypes("application/vnd.rim.cod cod");
		ftm.addMimeTypes("audio/vnd.lucent.voice lvp");
		ftm.addMimeTypes("application/vnd.intercon.formnet xpx");
		ftm.addMimeTypes("application/vnd.neurolanguage.nlu nlu");
		ftm.addMimeTypes("application/vnd.adobe.air-application-installer-package+zip air");
		ftm.addMimeTypes("video/jpm jpm");
		ftm.addMimeTypes("application/vnd.sun.xml.math sxm");
		ftm.addMimeTypes("application/x-mscardfile crd");
		ftm.addMimeTypes("application/x-authorware-map aam");
		ftm.addMimeTypes("application/vnd.fujixerox.docuworks.binder xbd");
		ftm.addMimeTypes("application/vnd.dna dna");
		ftm.addMimeTypes("application/vnd.osgi.dp dp");
		ftm.addMimeTypes("application/vnd.sun.xml.impress sxi");
		ftm.addMimeTypes("application/x-msbinder obd");
		ftm.addMimeTypes("application/applixware aw");
		ftm.addMimeTypes("application/marc mrc");
		ftm.addMimeTypes("image/pict pict");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.database odb");
		ftm.addMimeTypes("application/x-sv4crc sv4crc");
		ftm.addMimeTypes("text/x-fortran for");
		ftm.addMimeTypes("application/srgs+xml grxml");
		ftm.addMimeTypes("application/vnd.micrografx.igx igx");
		ftm.addMimeTypes("application/vnd.groove-account gac");
		ftm.addMimeTypes("application/java class");
		ftm.addMimeTypes("application/x-t3vm-image t3");
		ftm.addMimeTypes("application/gpx+xml gpx");
		ftm.addMimeTypes("application/vnd.nokia.n-gage.symbian.install n-gage");
		ftm.addMimeTypes("model/vnd.collada+xml dae");
		ftm.addMimeTypes("application/x-nzb nzb");
		ftm.addMimeTypes("text/vnd.dvb.subtitle sub");
		ftm.addMimeTypes("video/mp4 mpg4");
		ftm.addMimeTypes("application/vnd.cinderella cdy");
		ftm.addMimeTypes("application/vnd.mobius.plc plc");
		ftm.addMimeTypes("application/inkml+xml inkml");
		ftm.addMimeTypes("application/vnd.lotus-notes nsf");
		ftm.addMimeTypes("application/vnd.osgi.subsystem esa");
		ftm.addMimeTypes("application/x-latex latex");
		ftm.addMimeTypes("application/vnd.stardivision.writer-global sgl");
		ftm.addMimeTypes("application/vnd.mobius.daf daf");
		ftm.addMimeTypes("application/x-aim aim");
		ftm.addMimeTypes("application/vnd.genomatix.tuxedo txd");
		ftm.addMimeTypes("application/vnd.wap.wmlc wmlc");
		ftm.addMimeTypes("image/vnd.dece.graphic uvvi");
		ftm.addMimeTypes("application/x-gzip gz");
		ftm.addMimeTypes("application/font-tdpfr pfr");
		ftm.addMimeTypes("application/sparql-query rq");
		ftm.addMimeTypes("audio/x-mpeg mpega");
		ftm.addMimeTypes("application/x-cpio cpio");
		ftm.addMimeTypes("application/mads+xml mads");
		ftm.addMimeTypes("application/vnd.3gpp.pic-bw-var pvb");
		ftm.addMimeTypes("application/x-futuresplash spl");
		ftm.addMimeTypes("image/x-jg art");
		ftm.addMimeTypes("application/x-dtbresource+xml res");
		ftm.addMimeTypes("application/vnd.rn-realmedia rm");
		ftm.addMimeTypes("application/vnd.geogebra.file ggb");
		ftm.addMimeTypes("application/vnd.americandynamics.acc acc");
		ftm.addMimeTypes("application/vnd.ms-excel xlw");
		ftm.addMimeTypes("application/smil+xml smil");
		ftm.addMimeTypes("application/vnd.stepmania.package smzip");
		ftm.addMimeTypes("image/tiff tiff");
		ftm.addMimeTypes("application/vnd.vsf vsf");
		ftm.addMimeTypes("application/x-tex tex");
		ftm.addMimeTypes("application/vnd.android.package-archive apk");
		ftm.addMimeTypes("application/vnd.ibm.secure-container sc");
		ftm.addMimeTypes("application/vnd.sun.xml.writer.global sxg");
		ftm.addMimeTypes("application/mbox mbox");
		ftm.addMimeTypes("chemical/x-csml csml");
		ftm.addMimeTypes("text/vnd.curl curl");
		ftm.addMimeTypes("application/rls-services+xml rs");
		ftm.addMimeTypes("image/vnd.dxf dxf");
		ftm.addMimeTypes("application/x-hdf hdf");
		ftm.addMimeTypes("application/x-apple-diskimage dmg");
		ftm.addMimeTypes("application/mediaservercontrol+xml mscml");
		ftm.addMimeTypes("audio/webm weba");
		ftm.addMimeTypes("application/vnd.xfdl xfdl");
		ftm.addMimeTypes("application/vnd.crick.clicker.palette clkp");
		ftm.addMimeTypes("application/vnd.llamagraphics.life-balance.desktop lbd");
		ftm.addMimeTypes("application/mathml+xml mathml");
		ftm.addMimeTypes("application/cdmi-container cdmic");
		ftm.addMimeTypes("application/vnd.astraea-software.iota iota");
		ftm.addMimeTypes("application/vnd.businessobjects rep");
		ftm.addMimeTypes("application/timestamped-data tsd");
		ftm.addMimeTypes("application/sdp sdp");
		ftm.addMimeTypes("audio/x-wav wav");
		ftm.addMimeTypes("application/vnd.frogans.fnc fnc");
		ftm.addMimeTypes("application/xslt+xml xslt");
		ftm.addMimeTypes("application/vnd.crick.clicker.keyboard clkk");
		ftm.addMimeTypes("image/vnd.dwg dwg");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.presentationml.template potx");
		ftm.addMimeTypes("application/vnd.trueapp tra");
		ftm.addMimeTypes("application/vnd.mobius.msl msl");
		ftm.addMimeTypes("video/3gpp2 3g2");
		ftm.addMimeTypes("application/pkix-pkipath pkipath");
		ftm.addMimeTypes("application/x-tcl tcl");
		ftm.addMimeTypes("application/vnd.novadigm.edm edm");
		ftm.addMimeTypes("application/vnd.yamaha.openscoreformat.osfpvg+xml osfpvg");
		ftm.addMimeTypes("audio/x-ms-wax wax");
		ftm.addMimeTypes("application/vnd.previewsystems.box box");
		ftm.addMimeTypes("application/vnd.clonk.c4group c4u");
		ftm.addMimeTypes("application/x-texinfo texinfo");
		ftm.addMimeTypes("application/jsonml+json jsonml");
		ftm.addMimeTypes("image/svg+xml svgz");
		ftm.addMimeTypes("application/vnd.mobius.txf txf");
		ftm.addMimeTypes("application/xcap-diff+xml xdf");
		ftm.addMimeTypes("application/vnd.recordare.musicxml+xml musicxml");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.text-master odm");
		ftm.addMimeTypes("application/vnd.epson.msf msf");
		ftm.addMimeTypes("application/vnd.ms-powerpoint.template.macroenabled.12 potm");
		ftm.addMimeTypes("application/vnd.proteus.magazine mgz");
		ftm.addMimeTypes("application/vnd.ms-word.template.macroenabled.12 dotm");
		ftm.addMimeTypes("application/x-font-type1 pfm");
		ftm.addMimeTypes("image/x-portable-graymap pgm");
		ftm.addMimeTypes("application/x-tar tar");
		ftm.addMimeTypes("application/vnd.unity unityweb");
		ftm.addMimeTypes("application/vnd.sema sema");
		ftm.addMimeTypes("video/vnd.mpegurl mxu");
		ftm.addMimeTypes("application/vnd.semd semd");
		ftm.addMimeTypes("application/vnd.semf semf");
		ftm.addMimeTypes("application/vnd.sailingtracker.track st");
		ftm.addMimeTypes("application/mac-compactpro cpt");
		ftm.addMimeTypes("application/vnd.jcp.javame.midlet-rms rms");
		ftm.addMimeTypes("image/ief ief");
		ftm.addMimeTypes("application/vnd.novadigm.edx edx");
		ftm.addMimeTypes("application/sbml+xml sbml");
		ftm.addMimeTypes("audio/midi rmi");
		ftm.addMimeTypes("application/gxf gxf");
		ftm.addMimeTypes("application/x-gramps-xml gramps");
		ftm.addMimeTypes("application/vnd.micrografx.flo flo");
		ftm.addMimeTypes("application/x-chat chat");
		ftm.addMimeTypes("application/exi exi");
		ftm.addMimeTypes("image/jpeg jpg");
		ftm.addMimeTypes("application/pkix-crl crl");
		ftm.addMimeTypes("application/vnd.pmi.widget wg");
		ftm.addMimeTypes("video/x-fli fli");
		ftm.addMimeTypes("video/x-rad-screenplay avx");
		ftm.addMimeTypes("application/vnd.hp-pclxl pclxl");
		ftm.addMimeTypes("application/x-cdlink vcd");
		ftm.addMimeTypes("image/x-freehand fhc");
		ftm.addMimeTypes("application/cdmi-capability cdmia");
		ftm.addMimeTypes("video/x-ms-wm wm");
		ftm.addMimeTypes("application/vnd.stardivision.calc sdc");
		ftm.addMimeTypes("application/scvp-cv-response scs");
		ftm.addMimeTypes("application/vnd.airzip.filesecure.azf azf");
		ftm.addMimeTypes("audio/vnd.rip rip");
		ftm.addMimeTypes("image/webp webp");
		ftm.addMimeTypes("application/x-glulx ulx");
		ftm.addMimeTypes("application/x-xliff+xml xlf");
		ftm.addMimeTypes("application/vnd.airzip.filesecure.azs azs");
		ftm.addMimeTypes("application/x-ace-compressed ace");
		ftm.addMimeTypes("application/x-ustar ustar");
		ftm.addMimeTypes("video/x-dv dv");
		ftm.addMimeTypes("application/x-font-bdf bdf");
		ftm.addMimeTypes("application/x-font-pcf pcf");
		ftm.addMimeTypes("application/vnd.spotfire.sfs sfs");
		ftm.addMimeTypes("image/cgm cgm");
		ftm.addMimeTypes("text/tab-separated-values tsv");
		ftm.addMimeTypes("application/onenote onetoc2");
		ftm.addMimeTypes("application/vnd.stardivision.math smf");
		ftm.addMimeTypes("application/vnd.geogebra.tool ggt");
		ftm.addMimeTypes("application/vnd.muvee.style msty");
		ftm.addMimeTypes("application/vnd.flographit gph");
		ftm.addMimeTypes("application/vnd.ms-powerpoint ppt");
		ftm.addMimeTypes("image/gif gif");
		ftm.addMimeTypes("image/x-quicktime qtif");
		ftm.addMimeTypes("application/vnd.sun.xml.writer.template stw");
		ftm.addMimeTypes("image/x-portable-anymap pnm");
		ftm.addMimeTypes("application/vnd.is-xpr xpr");
		ftm.addMimeTypes("audio/vnd.dts.hd dtshd");
		ftm.addMimeTypes("application/vnd.accpac.simply.aso aso");
		ftm.addMimeTypes("application/vnd.epson.quickanime qam");
		ftm.addMimeTypes("image/g3fax g3");
		ftm.addMimeTypes("video/vnd.dece.pd uvvp");
		ftm.addMimeTypes("application/rss+xml rss");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.graphics odg");
		ftm.addMimeTypes("application/vnd.ms-htmlhelp chm");
		ftm.addMimeTypes("application/json json");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.presentationml.slideshow ppsx");
		ftm.addMimeTypes("application/vnd.aristanetworks.swi swi");
		ftm.addMimeTypes("application/vnd.kde.karbon karbon");
		ftm.addMimeTypes("application/x-mspublisher pub");
		ftm.addMimeTypes("application/x-ms-application application");
		ftm.addMimeTypes("application/vnd.adobe.xdp+xml xdp");
		ftm.addMimeTypes("application/vnd.palm pqa");
		ftm.addMimeTypes("application/vnd.fdf fdf");
		ftm.addMimeTypes("image/x-xwindowdump xwd");
		ftm.addMimeTypes("application/xaml+xml xaml");
		ftm.addMimeTypes("application/msword dot");
		ftm.addMimeTypes("application/vnd.3gpp2.tcap tcap");
		ftm.addMimeTypes("video/x-flv flv");
		ftm.addMimeTypes("text/x-c hh");
		ftm.addMimeTypes("application/vnd.osgeo.mapguide.package mgp");
		ftm.addMimeTypes("video/vnd.vivo viv");
		ftm.addMimeTypes("application/vnd.realvnc.bed bed");
		ftm.addMimeTypes("application/vnd.dvb.service svc");
		ftm.addMimeTypes("application/epub+zip epub");
		ftm.addMimeTypes("model/vnd.gdl gdl");
		ftm.addMimeTypes("application/vnd.ms-excel.sheet.binary.macroenabled.12 xlsb");
		ftm.addMimeTypes("application/cdmi-domain cdmid");
		ftm.addMimeTypes("text/plain txt");
		ftm.addMimeTypes("application/vnd.shana.informed.interchange iif");
		ftm.addMimeTypes("application/wsdl+xml wsdl");
		ftm.addMimeTypes("application/vnd.intergeo i2g");
		ftm.addMimeTypes("application/vnd.stardivision.writer vor");
		ftm.addMimeTypes("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet xlsx");
		ftm.addMimeTypes("application/vnd.ms-excel.addin.macroenabled.12 xlam");
		ftm.addMimeTypes("text/x-sfv sfv");
		ftm.addMimeTypes("application/lost+xml lostxml");
		ftm.addMimeTypes("application/vnd.fujixerox.docuworks xdw");
		ftm.addMimeTypes("application/vnd.ms-powerpoint.addin.macroenabled.12 ppam");
		ftm.addMimeTypes("application/vnd.hbci hbci");
		ftm.addMimeTypes("image/vnd.ms-photo wdp");
		ftm.addMimeTypes("application/vnd.geospace g3w");
		ftm.addMimeTypes("application/vnd.acucobol acu");
		ftm.addMimeTypes("image/x-icon ico");
		ftm.addMimeTypes("application/vnd.wap.wmlscriptc wmlsc");
		ftm.addMimeTypes("video/ogg ogv");
		ftm.addMimeTypes("application/vnd.crick.clicker.wordbank clkw");
		ftm.addMimeTypes("application/vnd.pawaafile paw");
		ftm.addMimeTypes("image/vnd.fastbidsheet fbs");
		ftm.addMimeTypes("video/x-sgi-movie movie");
		ftm.addMimeTypes("application/java-archive jar");
		ftm.addMimeTypes("application/emma+xml emma");
		ftm.addMimeTypes("application/x-font-woff woff");
		ftm.addMimeTypes("video/vnd.dece.sd uvvs");
		ftm.addMimeTypes("application/vnd.kahootz ktz");
		ftm.addMimeTypes("application/x-msclip clp");
		ftm.addMimeTypes("application/vnd.google-earth.kmz kmz");
		ftm.addMimeTypes("application/vnd.cloanto.rp9 rp9");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.spreadsheet ods");
		ftm.addMimeTypes("application/x-stuffit sit");
		ftm.addMimeTypes("application/vnd.oasis.opendocument.text-template ott");
		ftm.addMimeTypes("video/annodex axv");
		ftm.addMimeTypes("application/pkcs10 p10");
		ftm.addMimeTypes("video/x-ms-vob vob");
		ftm.addMimeTypes("audio/x-caf caf");
		ftm.addMimeTypes("video/vnd.ms-playready.media.pyv pyv");
		ftm.addMimeTypes("application/x-shar shar");
		ftm.addMimeTypes("application/sparql-results+xml srx");
		ftm.addMimeTypes("application/vnd.denovo.fcselayout-link fe_launch");
		ftm.addMimeTypes("application/scvp-cv-request scq");
		ftm.addMimeTypes("application/vnd.crick.clicker clkx");
		ftm.addMimeTypes("application/vnd.accpac.simply.imp imp");
		ftm.addMimeTypes("application/vnd.lotus-1-2-3 123");
		ftm.addMimeTypes("application/vnd.ipunplugged.rcprofile rcprofile");
		ftm.addMimeTypes("application/vnd.shana.informed.package ipk");
		ftm.addMimeTypes("application/oda oda");
	}
}
