(function($) {
    $.extend({
        myTime: {
            /**
             * 当前时间戳
             * @return <int>        unix时间戳(秒)  
             */
            CurTime: function(){
                return Date.parse(new Date())/1000;
            },
            /**              
             * 日期 转换为 Unix时间戳
             * @param <string> 2014-01-01 20:20:20  日期格式              
             * @return <int>        unix时间戳(秒)              
             */
            DateToUnix: function(string) {
                var f = string.split(' ', 2);
                var d = (f[0] ? f[0] : '').split('-', 3);
                var t = (f[1] ? f[1] : '').split(':', 3);
                return (new Date(
                        parseInt(d[0], 10) || null,
                        (parseInt(d[1], 10) || 1) - 1,
                        parseInt(d[2], 10) || null,
                        parseInt(t[0], 10) || null,
                        parseInt(t[1], 10) || null,
                        parseInt(t[2], 10) || null
                        )).getTime() / 1000;
            },
            
            /**              
             * 时间戳转换日期              
             * @param <int> unixTime    待时间戳(秒)              
             * @param <bool> isFull    返回完整时间(Y-m-d 或者 Y-m-d H:i:s)              
             * @param <int>  timeZone   时区              
             */
            UnixToDate: function(unixTime, isFull, timeZone) {
            	if(unixTime == null){
            		return "";
            	}
                if (typeof (timeZone) == 'number')
                {
                    unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60;
                }
                var time = new Date(unixTime);
                var ymdhis = "";
                var _m,_d,_h,_ms,_s;
                
                ymdhis += time.getFullYear() + "-";
                _m = time.getMonth()+1;
                ymdhis += (_m<10?"0"+_m:_m) + "-";
                _d = time.getDay();
                ymdhis += _d<10?"0"+_d:_d;
                if (isFull === true)
                {
                	_h = time.getHours();
                    ymdhis += " " + (_h<10?"0"+_h:_h) + ":";
                    _ms = time.getMinutes();
                    ymdhis += (_ms<10?"0"+_ms:_ms) + ":";
                    _s = time.getSeconds();
                    ymdhis += (_s<10?"0"+_s:_s);
                }
                return ymdhis;
            }          
            
        }
    });
})(jQuery);