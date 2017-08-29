package com.island;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class Server
{
	public static final String toast="0";
	public static final String schermo="1";
	public static final String apri="2";
	public static final String apriFile="3";
	public static final String directory="4";
	public static final String minifile="5";
	public static final String applicazioni="6";
	public static final String fine=toast;
	private Lan lan;
	private String[]comandi=new String[]{toast,schermo,apri,apriFile,directory,minifile,applicazioni};
	public Server(final String ip,final int porta)
	{
		lan=new Lan()
		{
			private String comando;
			private String var1;
			private String[]nomi;
			private List<ResolveInfo>lista;
			private PackageManager pm;
			public void leggi(final StringBuilder s,final Socket id)
			{
				if(comando==null)
				{
					if(s.equals(applicazioni))
					{
						pm=schermo().getPackageManager();
						Intent i=new Intent(Intent.ACTION_MAIN,null);
						i.addCategory(Intent.CATEGORY_LAUNCHER);
						lista=pm.queryIntentActivities(i,0);
						nomi=new String[lista.size()];
						for(int a=0;a<lista.size();a++)manda(nomi[a]=lista.get(a).loadLabel(pm).toString());
						manda(fine);
					}
					else comando=s.toString();
				}
				else if(comando.equals(toast))
				{
					schermo().runOnUiThread(new Runnable()
						{
							public void run()
							{
								Toast.makeText(schermo(),s,0).show();
							}
						});
					comando=null;
				}
				else if(comando.equals(schermo))
				{
					View v;
					v=schermo().getWindow().getDecorView().getRootView();
					v.setDrawingCacheEnabled(true);
					final int dimensioni=Integer.valueOf(s.toString());
					Bitmap b=Bitmap.createScaledBitmap(v.getDrawingCache(),dimensioni,dimensioni,false);
					v.setDrawingCacheEnabled(false);
					manda(schermo);
					try
					{
						Thread.sleep(20);
					}
					catch(InterruptedException e)
					{
						Lista.debug(e);
					}
					try
					{
						OutputStream output=id.getOutputStream();
						int primo=b.getPixel(0,0);
						int n=0;
						int r=Color.red(primo);
						int g=Color.green(primo);
						int blu=Color.blue(primo);
						for(int x=0;x<b.getWidth();x++)for(int y=0;y<b.getHeight();y++)
							{
								int pixel=b.getPixel(x,y);
								n++;
								int nr=Color.red(pixel);
								int ng=Color.green(pixel);
								int nb=Color.blue(pixel);
								if(r!=nr||g!=ng||blu!=nb||n==255||(x==b.getWidth()-1&&y==b.getHeight()-1))
								{
									output.write(n);
									output.write(r);
									output.write(g);
									output.write(blu);
									n=0;
									r=nr;
									g=ng;
									blu=nb;
								}
							}
					}
					catch(IOException e)
					{
						Lista.debug(e);
					}
					b.recycle();
					comando=null;
				}
				else if(comando.equals(apri))
				{
					for(int a=0;a<nomi.length;a++)if(nomi[a].equals(s))
						{
							schermo().startActivity(pm.getLaunchIntentForPackage(lista.get(a).activityInfo.packageName));
							break;
						}
					comando=null;
				}
				else if(comando.equals(directory))
				{
					String[]file=new File(s.toString()).list();
					if(file==null)file=new String[0];
					for(String f:file)
					{
						if(new File(s+f).isDirectory())manda(f+"/");
						else manda(f);
					}
					manda(fine);
					comando=null;
				}
				else if(comando.equals(apriFile))
				{
					File f=new File(s.toString());
					try
					{
						OutputStream output=id.getOutputStream();
						FileInputStream input=new FileInputStream(f);
						long dim=f.length();
						manda(String.valueOf(dim));
						try
						{
							Thread.sleep(20);
						}
						catch(InterruptedException e)
						{
							Lista.debug(e);
						}
						for(long a=0;a<dim;a++)output.write(input.read());
						input.close();
						comando=null;
					}
					catch(IOException e)
					{
						Lista.debug(e);
					}
				}
				else if(comando.equals(minifile))
				{
					if(var1==null)var1=s.toString();
					else
					{
						try
						{
							final int dimensioni=Integer.valueOf(var1);
							BitmapFactory.Options opt=new BitmapFactory.Options();
							opt.inJustDecodeBounds=true;
							FileInputStream input=new FileInputStream(new File(s.toString()));
							BitmapFactory.decodeStream(input,null,opt);
							input.close();
							double scalatura=0.5;
							while(opt.outWidth/(scalatura*2)>dimensioni&&opt.outHeight/(scalatura*2)>dimensioni)scalatura*=2;
							opt.inSampleSize=(int)(scalatura*2);
							opt.inJustDecodeBounds=false;
							input=new FileInputStream(new File(s.toString()));
							Bitmap b1=BitmapFactory.decodeStream(input,null,opt);
							input.close();
							Bitmap b=Bitmap.createScaledBitmap(b1,dimensioni,dimensioni,false);
							b1.recycle();
							manda(schermo);
							try
							{
								Thread.sleep(20);
							}
							catch(InterruptedException e)
							{
								Lista.debug(e);
							}
							OutputStream output=id.getOutputStream();
							int primo=b.getPixel(0,0);
							int n=0;
							int r=Color.red(primo);
							int g=Color.green(primo);
							int blu=Color.blue(primo);
							for(int x=0;x<b.getWidth();x++)for(int y=0;y<b.getHeight();y++)
								{
									int pixel=b.getPixel(x,y);
									n++;
									int nr=Color.red(pixel);
									int ng=Color.green(pixel);
									int nb=Color.blue(pixel);
									if(r!=nr||g!=ng||blu!=nb||n==255||(x==b.getWidth()-1&&y==b.getHeight()-1))
									{
										output.write(n);
										output.write(r);
										output.write(g);
										output.write(blu);
										n=0;
										r=nr;
										g=ng;
										blu=nb;
									}
								}
							b.recycle();
							comando=null;
							var1=null;
						}
						catch(IOException e)
						{
							Lista.debug(e);
						}
					}
				}
			}
		};
		final Handler h=new Handler();
		h.postDelayed(new Runnable()
			{
				public void run()
				{
					if(!lan.connesso())lan.inizioClient(ip,porta);
					h.postDelayed(this,10000);
				}
			},1);
	}
	public void schermo(Schermo schermo)
	{
		lan.schermo(schermo);
	}
}
