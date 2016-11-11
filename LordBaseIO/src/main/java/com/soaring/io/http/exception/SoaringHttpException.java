/*  1:   */ package com.soaring.io.http.exception;
/*  2:   */ 
/*  3:   */ public class SoaringHttpException
/*  4:   */   extends SoaringException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 1L;
/*  7:   */   private final int mStatusCode;
/*  8:   */   
/*  9:   */   public SoaringHttpException(String message, int statusCode)
/* 10:   */   {
/* 11:39 */     super(message);
/* 12:40 */     this.mStatusCode = statusCode;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public int getStatusCode()
/* 16:   */   {
/* 17:49 */     return this.mStatusCode;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\Administrator\Desktop\weibosdkcore.jar
 * Qualified Name:     com.sina.weibo.sdk.exception.WeiboHttpException
 * JD-Core Version:    0.7.0.1
 */