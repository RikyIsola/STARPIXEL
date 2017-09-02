package com.island.starpixel;
import android.graphics.*;
import android.view.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.solidi.mobili.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import com.island.starpixel.blocchi.solidi.torcia.*;
import com.island.starpixel.blocchi.strumenti.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
import com.island.starpixel.blocchi.strumenti.scudi.*;
import com.island.starpixel.blocchi.terreni.*;
public class EntitaLan extends Oggetto
{
	Blocco b;
	ChunkLan chunk;
	int img,luce,luminosita;
	final int riduzione;
	private boolean layout;
	EntitaLan coperto;
	/*Bitmap cache;
	Canvas cachecanvas;*/
	EntitaLan(final ChunkLan c,double x,double y,Blocco b)
	{
		super(c,x,y,x+1,y+1);
		/*cache=Bitmap.createBitmap(25,25,Bitmap.Config.ARGB_8888);
		cachecanvas=new Canvas(cache);*/
		if(!(b instanceof Scudi))
		{
			schermo().runOnUiThread(new Runnable()
			{
				public void run()
				{
					c.removeView(EntitaLan.this);
				}
			});
		}
		else
		{
			double menoX=0;
			double menoY=0;
			double piuX=0;
			double piuY=0;
			for(EntitaLan e1:c.entita)
			{
				if(e1.x()<menoX)menoX=e1.x();
				if(e1.y()<menoY)menoY=e1.y();
				if(e1.larghezza()>piuX)piuX=e1.larghezza();
				if(e1.altezza()>piuY)piuY=e1.altezza();
			}
			x(menoX).y(menoY).larghezza(piuX).altezza(piuY);
		}
		chunk=c;
		if(b instanceof Torcia||(b instanceof Lanciato&&((Lanciato)b).luminoso()))luminosita=255;
		if(b==c.gioco.schermo().blocchi.LuceElettrica)riduzione=50;
		else riduzione=100;
		if(b==c.gioco.schermo().blocchi.Giocatore)
		{
			c.gioco.tu=this;
			c.gioco.superordine=true;
		}
		c.entita=Lista.aggiungi(c.entita,this);
		if(b instanceof Lanciato)immagine(((Lanciato)b).proiettile());
		else immagine(b.immagine());
		this.b=b;
		if(b instanceof Strumento)
		{
			Strumento s=(Strumento)b;
			if(s.suono()!=0&&!Suono.esiste(schermo(),s.suono()))new Suono(schermo(),s.suono()).start();
			else if(s==c.gioco.schermo().blocchi.LuceTeletrasporto&&!c.gioco.teletrasportando)
			{
				c.gioco.teletrasportando=true;
				new Suono(schermo(),R.raw.alien_teleport)
				{
					public Suono rilascia()
					{
						c.gioco.teletrasportando=false;
						super.rilascia();
						return this;
					}
				}.start();
			}
		}
	}
	void rottura(int n)
	{
		if(n==0)img=0;
		else if(n==1)img=R.drawable.rottura1;
		else if(n==2)img=R.drawable.rottura2;
		else
		{
			img=R.drawable.rottura3;
			if(b==chunk.gioco.schermo().blocchi.Zombie)new Suono(schermo(),R.raw.zombie_attack).start();
		}
		concatena2(img);
		schermo().runOnUiThread(invalida);
	}
	int rottura()
	{
		double n;
		if(img==0)n=1;
		else if(img==R.drawable.rottura1)n=2.0/3.0;
		else if(img==R.drawable.rottura2)n=1.0/3.0;
		else n=0;
		return(int)(b.vita()*n);
	}
	public void sempre()
	{
		if(b==chunk.gioco.schermo().blocchi.Esplosione||b instanceof Scudi||b==chunk.gioco.schermo().blocchi.LuceTeletrasporto)
		{
			alpha(alpha()-255/b.vita());
			if(b==chunk.gioco.schermo().blocchi.LuceTeletrasporto||b==chunk.gioco.schermo().blocchi.Esplosione)luminosita=alpha();
			if(alpha()<=0)
			{
				try
				{
					chunk.entita=Lista.rimuovi(chunk.entita,this);
					rimosso();
					schermo().runOnUiThread(new Runnable()
					{
						public void run()
						{
							chunk.removeView(EntitaLan.this);
						}
					});
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					Lista.debug(e);
				}
			}
		}
		if(layout)
		{
			if(Oggetto.distanza(x(),y(),larghezza(),altezza(),chunk.transX(),chunk.transY(),chunk.maxX()+chunk.transX(),chunk.maxY()+chunk.transY())>0)
			{
				if(!(b instanceof Scudi))schermo().runOnUiThread(rimuovi);
				layout=false;
			}
		}
		else
		{
			if(Oggetto.distanza(x(),y(),larghezza(),altezza(),chunk.transX(),chunk.transY(),chunk.maxX()+chunk.transX(),chunk.maxY()+chunk.transY())<=0)
			{
				if(!(b instanceof Scudi))schermo().runOnUiThread(aggiungi);
				layout=true;
			}
		}
	}
	public void onDraw(Canvas canvas)
	{
		schermo().inizioDebug(1);
		super.onDraw(canvas);
		schermo().fineDebug(1);
	}
	/*public void onDraw(Canvas canvas)
	{
		//super.onDraw(canvas);
		paint.reset();
		if(schermo().esiste(b.immagine()))
		{
			Bitmap b=schermo().immagine(this.b.immagine());
			if(!b.isRecycled())
			{
				if(coperto!=null&&!((Solido)this.b).coperto()&&schermo().esiste(coperto.b.immagine()))
				{
					Bitmap b1=schermo().immagine(coperto.b.immagine());
					if(!b1.isRecycled())
					{
						if(disegno==null)
						{
							disegno=Bitmap.createBitmap(100,100,Bitmap.Config.RGB_565);
							Canvas c=new Canvas(disegno);
							c.drawBitmap(b1,null,new Rect(0,0,c.getWidth(),c.getHeight()),paint);
							c.drawBitmap(b,null,new Rect(0,0,c.getWidth(),c.getHeight()),paint);
						}
						try
						{
							//cachecanvas.drawBitmap(b,null,new Rect(0,0,cachecanvas.getWidth(),cachecanvas.getHeight()),paint);
							canvas.drawBitmap(disegno,null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),paint);
						}
						catch(RuntimeException e)
						{
							Lista.debug(e);
						}
					}
				}
				else
				{
					try
					{
						//cachecanvas.drawBitmap(b,null,new Rect(0,0,cachecanvas.getWidth(),cachecanvas.getHeight()),paint);
						canvas.drawBitmap(b,null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),paint);
					}
					catch(RuntimeException e)
					{
						Lista.debug(e);
					}
				}
			}
		}
		if(schermo().esiste(img))
		{
			Bitmap b=schermo().immagine(img);
			if(!b.isRecycled())
			{
				try
				{
					//cachecanvas.drawBitmap(b,null,new Rect(0,0,cachecanvas.getWidth(),cachecanvas.getHeight()),paint);
					canvas.drawBitmap(b,null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),paint);
				}
				catch(RuntimeException e)
				{
					Lista.debug(e);
				}
			}
		}
		if(luce!=255)
		{
			paint.setAlpha(255-luce);
			if(b instanceof Terreno||(b instanceof Solido&&!(b instanceof Mobile)))
			{
				//cachecanvas.drawRect(0,0,cachecanvas.getWidth(),cachecanvas.getHeight(),paint);
				canvas.drawColor(paint.getColor());
			}
			else
			{
				if(!schermo().esiste(b.id+1))
				{
					if(schermo().esiste(b.immagine()))
					{
						Bitmap bitmap=Bitmap.createBitmap(25,25,Bitmap.Config.ALPHA_8);
						Canvas c=new Canvas(bitmap);
						paint.reset();
						c.drawBitmap(schermo().immagine(immagine()),null,new Rect(0,0,c.getWidth(),c.getHeight()),paint);
						schermo().carica(bitmap,b.id+1,bitmap.getWidth(),bitmap.getHeight());
						/*for(int x=0;x<bitmap.getWidth();x++)for(int y=0;y<bitmap.getHeight();y++)
						 {
						 int alpha=Color.alpha(bitmap.getPixel(x,y));
						 if(alpha!=0)bitmap.setPixel(x,y,Color.argb(alpha,0,0,0));
						 }
					}
				}
				else
				{
					//cachecanvas.drawBitmap(schermo().immagine(b.id+1),null,new Rect(0,0,cachecanvas.getWidth(),cachecanvas.getHeight()),paint);
					canvas.drawBitmap(schermo().immagine(b.id+1),null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),paint);
				}
			}
		}
	}*/
	public void rimosso()
	{
		if(coperto!=null)
		{
			final EntitaLan e=coperto;
			coperto=null;
			schermo().runOnUiThread(new Runnable()
				{
					public void run()
					{
						e.setVisibility(View.VISIBLE);
					}
				});
		}
		if(b instanceof Intelligente&&img==R.drawable.rottura3)
		{
			Intelligente i=(Intelligente)b;
			if(i.suono()!=0)new Suono(schermo(),i.suono()).start();
		}
		if(b instanceof Giocatore)
		{
			chunk.gioco.tu=null;
			if(schermo().finestra()instanceof Comandi)schermo().finestra().cancel();
		}
		if(b instanceof ConsoleDiComando&&chunk==chunk.gioco.tu.chunk)
		{
			if(schermo().finestra()instanceof Comandi)schermo().finestra().cancel();
		}
	}
	public Oggetto x(double x)
	{
		if(coperto!=null)
		{
			final EntitaLan e=coperto;
			immagine(concatenato());
			concatena(0);
			coperto=null;
			schermo().runOnUiThread(new Runnable()
				{
					public void run()
					{
						e.setVisibility(View.VISIBLE);
					}
				});
		}
		return super.x(x);
	}
	public Oggetto y(double y)
	{
		if(coperto!=null)
		{
			final EntitaLan e=coperto;
			coperto=null;
			immagine(concatenato());
			concatena(0);
			schermo().runOnUiThread(new Runnable()
				{
					public void run()
					{
						e.setVisibility(View.VISIBLE);
					}
				});
		}
		return super.y(y);
	}
	private Runnable rimuovi=new Runnable()
	{
		public void run()
		{
			chunk.removeView(EntitaLan.this);
		}
	};
	private Runnable aggiungi=new Runnable()
	{
		public void run()
		{
			chunk.addView(EntitaLan.this);
			chunk.gioco.ordine=true;
		}
	};
}
