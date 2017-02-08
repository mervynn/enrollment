$(function(){
	'use strict';


	// PACE SETUP
    // Progress autoatically with pace
    window.paceOptions = {
      // Disable the 'elements' source
      elements: false,

      // Only show the progress on regular and ajax-y page navigation,
      // not every request
      restartOnRequestAfter: false
    }
    window.Pace.start();
    // END PACE SETUP




    // LAZY LOAD
    $("img.lazy").lazyload({
        effect : "fadeIn"
    });
    // END LAZY LOAD




    // MASONRY
    // Takes the gutter width from the bottom margin of .masonry-item
    var gutter = parseInt( $( '.masonry-item' ).css( 'marginBottom' ) ); 
 
    // Creates an instance of Masonry on .masonry
    $(".masonry").masonry({
        // gutter: gutter,
        columnWidth: ".masonry-item", 
        itemSelector: ".masonry-item"
    });

    // generate data sortHelper
    $('.masonry').each(function(){
        var $masonry = $(this),
            items = $masonry.find('.masonry-item'),
            item_index = 0;

        items.each(function(){
            var item = $(this);

            item.attr('data-sort-helper', item_index);
            item_index++;
        });
    });

    $(document).on('click', '[data-toggle="side-right"]', function(){
        // trigger layout
        var isMasonry = ( $(document).find('.masonry').length > 0 ) ? true : false ;

        if (isMasonry) {
            $(".masonry").masonry('layout');
            // reLayout after animated
            $('.side-right').one("animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd", function(){
                $(".masonry").masonry('layout');
            });
            // reLayout after transition
            $(".side-right").one("transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd", function(){
                $(".masonry").masonry('layout');
            });
        };
    });

    // masonry filter
    var masonry_filter = 'all';
    $(document).on('change', '[name="masonry-filter"]', function(){
        var $this = $(this),
            target = $(this).data('masonryTarget'),
            $target = $(target),
            masonry_filter = $this.val(),
            items = $target.find('.masonry-item'),
            hide_items = $target.find('.masonry-item:not([data-filter*="' + masonry_filter + '"])'),
            reveal_items = $target.find('.masonry-item[data-filter*="' + masonry_filter + '"]');

        
        // swap element (fix bug on .masonry('layout') on hidden element)
        $(reveal_items[0]).insertBefore(items[0]);

        if (masonry_filter == 'all' || masonry_filter == '*') {
            items.removeClass('hide');
            masonry_filter = 'all';
        }
        else{
            hide_items.addClass('hide');
            reveal_items.removeClass('hide');
        }
        $target.masonry('layout');
    });

    // masonry search
    $(document).on('keyup', 'input[name="masonry-search"]', function(){
        var $this = $(this),
            target = $(this).data('masonryTarget'),
            $target = $(target),
            search_attr = $this.data('searchAttr'),
            search_val = $this.val(),
            items = $target.find('.masonry-item'),
            reveal_items = $target.find('.masonry-item[' + search_attr + '*="' + search_val + '"]');

        
        // swap element (fix bug on .masonry('layout') on hidden element)
        $(reveal_items[0]).insertBefore(items[0]);

        $('[name="masonry-filter"]').prop('checked', false)
            .parent().removeClass('active');
        $('[name="masonry-sort"]').prop('checked', false)
            .parent().removeClass('active');

        
        if (search_val == '') {
            items.removeClass('hide');

            $('[data-toggle="masonry-search-submit"]').removeClass('hide');
            $('[data-toggle="masonry-search-clear"]').addClass('hide');
        }
        else{
            items.addClass('hide');
            reveal_items.removeClass('hide');

            $('[data-toggle="masonry-search-submit"]').addClass('hide');
            $('[data-toggle="masonry-search-clear"]').removeClass('hide');
        }

        $target.masonry('layout');
    });
    $(document).on('submit', 'form[name="masonry-search"]', function(){
        return false;
    });
    $(document).on('click', '[data-toggle="masonry-search-clear"]', function(e){
        e.preventDefault();

        var $this = $(this),
            target = $(this).data('masonryTarget'),
            $target = $(target),
            items = $target.find('.masonry-item');

        $('input[name="masonry-search"]').val('');
        $(this).addClass('hide');
        $('[data-toggle="masonry-search-submit"]').removeClass('hide');

        items.removeClass('hide');
        $target.masonry('layout');
    });


    // masonry sortable
    $(document).on('change', '[name="masonry-sort"]', function(){
        var $this = $(this),
            target = $(this).data('masonryTarget'),
            $target = $(target),
            sort_attr = $this.data('sortAttr'),
            sort_type = $this.val(),
            items = $target.find('.masonry-item'),
            items_target = $target.find('.masonry-item:not(.hide)');


        // swap element (fix bug on .masonry('layout') on hidden element)
        if (!masonry_filter == 'all') {
            if (items_target.attr('data-sort-helper="0"').length > 0) {
                $(items_target).insertAfter(items[0]); // need each element here!!
            }
            else{
                $(items_target).insertBefore(items[0]);
            }
        }

        console.log(items_target)
        
        if (sort_type == 'asc') {
            items_target.sortElements(function(a, b){
                return $(a).attr(sort_attr) > $(b).attr(sort_attr) ? 1 : -1;
            });
        }
        else if (sort_type == 'desc') {

            items_target.sortElements(function(a, b){
                return $(a).attr(sort_attr) < $(b).attr(sort_attr) ? 1 : -1;
            });
        }
        else if(sort_type == 'false'){            
            items_target.sortElements(function(a, b){
                return $(a).data('sortHelper') > $(b).data('sortHelper') ? 1 : -1;
            });
        }

        $target.masonry('reloadItems').masonry();
        $target.masonry('layout');
    });
    // END MASONRY
    



    // NICE SCROLL SETUP
    $('[data-toggle="niceScroll"], .pre-scrollable').each(function(){
        var $this = $(this),
            wrapper = $this.attr('data-scroll-wrapper'),
            zindex = $this.attr('data-scroll-zindex'),
            cursoropacitymin = $this.attr('data-scroll-cursoropacitymin'),
            cursoropacitymax = $this.attr('data-scroll-cursoropacitymax'),
            cursorcolor = $this.attr('data-scroll-cursorcolor'),
            cursorwidth = $this.attr('data-scroll-cursorwidth'),
            cursorborder = $this.attr('data-scroll-cursorborder'),
            cursorborderradius = $this.attr('data-scroll-cursorborderradius'),
            scrollspeed = $this.attr('data-scroll-scrollspeed'),
            mousescrollstep = $this.attr('data-scroll-mousescrollstep'),
            touchbehavior = $this.attr('data-scroll-touchbehavior'),
            hwacceleration = $this.attr('data-scroll-hwacceleration'),
            usetransition = $this.attr('data-scroll-usetransition'),
            boxzoom = $this.attr('data-scroll-boxzoom'),
            dblclickzoom = $this.attr('data-scroll-dblclickzoom'),
            gesturezoom = $this.attr('data-scroll-gesturezoom'),
            grabcursorenabled = $this.attr('data-scroll-grabcursorenabled'),
            autohidemode = $this.attr('data-scroll-autohidemode'),
            background = $this.attr('data-scroll-background'),
            iframeautoresize = $this.attr('data-scroll-iframeautoresize'),
            cursorminheight = $this.attr('data-scroll-cursorminheight'),
            preservenativescrolling = $this.attr('data-scroll-preservenativescrolling'),
            railoffset = $this.attr('data-scroll-railoffset'),
            bouncescroll = $this.attr('data-scroll-bouncescroll'),
            spacebarenabled = $this.attr('data-scroll-spacebarenabled'),
            disableoutline = $this.attr('data-scroll-disableoutline'),
            horizrailenabled = $this.attr('data-scroll-horizrailenabled'),
            railalign = $this.attr('data-scroll-railalign'),
            railvalign = $this.attr('data-scroll-railvalign'),
            enabletranslate3d = $this.attr('data-scroll-enabletranslate3d'),
            enablemousewheel = $this.attr('data-scroll-enablemousewheel'),
            enablekeyboard = $this.attr('data-scroll-enablekeyboard'),
            smoothscroll = $this.attr('data-scroll-smoothscroll'),
            sensitiverail = $this.attr('data-scroll-sensitiverail'),
            enablemouselockapi = $this.attr('data-scroll-enablemouselockapi'),
            cursorfixedheight = $this.attr('data-scroll-cursorfixedheight'),      
            directionlockdeadzone = $this.attr('data-scroll-directionlockdeadzone'),
            hidecursordelay = $this.attr('data-scroll-hidecursordelay'),
            nativeparentscrolling = $this.attr('data-scroll-nativeparentscrolling'),
            enablescrollonselection = $this.attr('data-scroll-enablescrollonselection'),
            overflowx = $this.attr('data-scroll-overflowx'),
            overflowy = $this.attr('data-scroll-overflowy'),
            cursordragspeed =$this.attr('data-scroll-cursordragspeed'),
            rtlmode = $this.attr('data-scroll-rtlmode'),
            cursordragontouch = $this.attr('data-scroll-cursordragontouch'),
            oneaxismousemode = $this.attr('data-scroll-oneaxismousemode');

        // default for undefined
        zindex = (zindex === undefined) ? "auto" : zindex,
        cursoropacitymin = (cursoropacitymin === undefined) ? 0 : parseInt(cursoropacitymin),
        cursoropacitymax = (cursoropacitymax === undefined) ? 1 : parseInt(cursoropacitymax),
        cursorcolor = (cursorcolor === undefined) ? "rgba(255, 255, 255, 0.125)" : cursorcolor,
        cursorwidth = (cursorwidth === undefined) ? "12px" : cursorwidth,
        cursorborder = (cursorborder === undefined) ? "3px solid transparent" : cursorborder,
        cursorborderradius = (cursorborderradius === undefined) ? "6px" : cursorborderradius,
        scrollspeed = (scrollspeed === undefined) ? 100 : parseInt(scrollspeed),
        mousescrollstep = (mousescrollstep === undefined) ? 8*3 : parseInt(mousescrollstep),
        touchbehavior = (touchbehavior === undefined) ? false : touchbehavior.bool(),
        hwacceleration = (hwacceleration === undefined) ? true : hwacceleration.bool(),
        usetransition = (usetransition === undefined) ? true : usetransition.bool(),
        boxzoom = (boxzoom === undefined) ? false : boxzoom.bool(),
        dblclickzoom = (dblclickzoom === undefined) ? true : dblclickzoom.bool(),
        gesturezoom = (gesturezoom === undefined) ? true : gesturezoom.bool(),
        grabcursorenabled = (grabcursorenabled === undefined) ? true : grabcursorenabled.bool(),
        autohidemode = (autohidemode === undefined) ? false : autohidemode.bool(),
        background = (background === undefined) ? "" : background,
        iframeautoresize = (iframeautoresize === undefined) ? true : iframeautoresize.bool(),
        cursorminheight = (cursorminheight === undefined) ? 32 : parseInt(cursorminheight),
        preservenativescrolling = (preservenativescrolling === undefined) ? true : preservenativescrolling.bool(),
        railoffset = (railoffset === undefined) ? false : railoffset.bool(),
        bouncescroll = (bouncescroll === undefined) ? true : bouncescroll.bool(),
        spacebarenabled = (spacebarenabled === undefined) ? true : spacebarenabled.bool(),
        disableoutline = (disableoutline === undefined) ? true : disableoutline.bool(),
        horizrailenabled = (horizrailenabled === undefined) ? true : horizrailenabled.bool(),
        railalign = (railalign === undefined) ? "right" : railalign,
        railvalign = (railvalign === undefined) ? "bottom" : railvalign,
        enabletranslate3d = (enabletranslate3d === undefined) ? true : enabletranslate3d.bool(),
        enablemousewheel = (enablemousewheel === undefined) ? true : enablemousewheel.bool(),
        enablekeyboard = (enablekeyboard === undefined) ? true : enablekeyboard.bool(),
        smoothscroll = (smoothscroll === undefined) ? true : smoothscroll.bool(),
        sensitiverail = (sensitiverail === undefined) ? true : sensitiverail.bool(),
        enablemouselockapi = (enablemouselockapi === undefined) ? true : enablemouselockapi.bool(),
        cursorfixedheight = (cursorfixedheight === undefined) ? false : cursorfixedheight.bool() 
        directionlockdeadzone = (directionlockdeadzone === undefined) ? 6 : parseInt(directionlockdeadzone),
        hidecursordelay = (hidecursordelay === undefined) ? 400 : parseInt(hidecursordelay),
        nativeparentscrolling = (nativeparentscrolling === undefined) ? true : nativeparentscrolling.bool(),
        enablescrollonselection = (enablescrollonselection === undefined) ? true : enablescrollonselection.bool(),
        overflowx = (overflowx === undefined) ? true : overflowx.bool(),
        overflowy = (overflowy === undefined) ? true : overflowy.bool(),
        cursordragspeed = (cursordragspeed === undefined) ? 0.3 : parseInt(cursordragspeed),
        rtlmode = (rtlmode === undefined) ? "auto" : rtlmode,
        cursordragontouch = (cursordragontouch === undefined) ? false : cursordragontouch.bool(),
        oneaxismousemode = (oneaxismousemode === undefined) ? "auto" : oneaxismousemode;

        var scroll_settings = {
            zindex: zindex,
            cursoropacitymin: cursoropacitymin,
            cursoropacitymax: cursoropacitymax,
            cursorcolor: cursorcolor,
            cursorwidth: cursorwidth,
            cursorborder: cursorborder,
            cursorborderradius: cursorborderradius,
            scrollspeed: scrollspeed,
            mousescrollstep: mousescrollstep,
            touchbehavior: touchbehavior,
            hwacceleration: hwacceleration,
            usetransition: usetransition,
            boxzoom: boxzoom,
            dblclickzoom: dblclickzoom,
            gesturezoom: gesturezoom,
            grabcursorenabled: grabcursorenabled,
            autohidemode: autohidemode,
            background: background,
            iframeautoresize: iframeautoresize,
            cursorminheight: cursorminheight,
            preservenativescrolling: preservenativescrolling,
            railoffset: railoffset,
            bouncescroll: bouncescroll,
            spacebarenabled: spacebarenabled,
            disableoutline: disableoutline,
            horizrailenabled: horizrailenabled,
            railalign: railalign,
            railvalign: railvalign,
            enabletranslate3d: enabletranslate3d,
            enablemousewheel: enablemousewheel,
            enablekeyboard: enablekeyboard,
            smoothscroll: smoothscroll,
            sensitiverail: sensitiverail,
            enablemouselockapi: enablemouselockapi,
            cursorfixedheight: cursorfixedheight,
            directionlockdeadzone: directionlockdeadzone,
            hidecursordelay: hidecursordelay,
            nativeparentscrolling: nativeparentscrolling,
            enablescrollonselection: enablescrollonselection,
            overflowx: overflowx,
            overflowy: overflowy,
            cursordragspeed: cursordragspeed,
            rtlmode: rtlmode,
            cursordragontouch: cursordragontouch,
            oneaxismousemode: oneaxismousemode
        }

        // initialize niceScroll
        if (wrapper === undefined) {
            if ($this.hasClass('pre-scrollable')) {
                
                if (!$this.hasClass('nicescroll-off')) {
                    $this.wrapInner( "<div class='pre-scrollable-wrapper'></div>" );
                    var scrollWrapper = $this.find('.pre-scrollable-wrapper');

                    scroll_settings.cursorcolor = 'rgba(0, 0, 0, .1)';

                    $this.niceScroll(scrollWrapper, scroll_settings);
                }
            }
            else{
                
                $this.niceScroll(scroll_settings);
            }

        }
        else{
            var scrollWrapper = $this.find(wrapper);
            $this.niceScroll(scrollWrapper, scroll_settings);
        }
    });
    // END NICE SCROLL
    


    // SPARKLINE SETUP
    $('.sparkline-bar').each(function(){
        var $this = $(this),
            text = $this.attr('data-value'),
            value = text.replace(/\s+/g, ''),
            data = value.split(","),
            height = $this.attr('data-height'),
            width = $this.attr('data-width'),
            barColor = $this.attr('data-barColor'),
            negBarColor = $this.attr('data-negBarColor'),
            zeroColor = $this.attr('data-zeroColor'),
            nullColor = $this.attr('data-nullColor'),
            barWidth = $this.attr('data-barWidth'),
            barSpacing = $this.attr('data-barSpacing'),
            stackedBarColor = $this.attr('data-stackedBarColor');

        height = (height === undefined) ? 'auto' : height,
        width = (width === undefined) ? 'auto' : width,
        barColor = (barColor === undefined) ? '#13A89E' : barColor,
        negBarColor = (negBarColor === undefined) ? '#DA4F49' : negBarColor,
        barWidth = (barWidth === undefined) ? 4 : parseInt(barWidth),
        barSpacing = (barSpacing === undefined) ? 1 : parseInt(barSpacing);

        $this.sparkline(data, {
            type: 'bar',
            height: height,
            width: width,
            barColor: barColor,
            negBarColor: negBarColor,
            zeroColor: zeroColor,
            nullColor: nullColor,
            barWidth: barWidth,
            barSpacing: barSpacing,
            stackedBarColor: stackedBarColor
        });
    });
	// END SPARKLINE SETUP
	



	// FORM STUFF
	// AUTOGROW TEXTAREA
    $('.autogrow').autoGrow();
    // END FORM STUFF
    




    // DATATABLES
    $('.datatable').each(function(){
        var $this = $(this),
            page = ($this.data('page')) ? $this.data('page') : 'bs_full',
            source = ($this.data('source')) ? $this.data('source') : false,
            isAjax = (source) ? true : false ;

        $this.dataTable({
            "sPaginationType": page,
            "bProcessing": isAjax,
            "sAjaxSource": source
        });
    });
    $('.datatable-tools').each(function(){
        var $this = $(this),
            page = ($this.data('page')) ? $this.data('page') : 'bs_full',
            source = ($this.data('source')) ? $this.data('source') : false,
            isAjax = (source) ? true : false ;

        $this.dataTable({
            "sPaginationType": page,
            "bProcessing": isAjax,
            "sAjaxSource": source,
            "sDom": "<'TTT_btn-group-wrapper'T><'row'<'col-sm-12'<'pull-right'f><'pull-left'l>r<'clearfix'>>>t<'row'<'col-sm-12'<'pull-left'i><'pull-right'p><'clearfix'>>>",
            "oTableTools": {
                "aButtons": [
                    "copy",
                    "print",
                    {
                        "sExtends":    "collection",
                        "sButtonText": 'Save <i class="fa fa-angle-down"></i>',
                        "aButtons":    [ 
                            "xls", 
                            "csv",
                            {
                                "sExtends": "pdf",
                                // "sPdfMessage": "Your custom message would go here.",
                                "sPdfOrientation": "landscape"
                            }
                        ]
                    }
                ],
                "sSwfPath": "scripts/swf/copy_csv_xls_pdf.swf"
            }
        });
    });
    $('.datatable, .datatable-tools').each(function(){
        var datatable = $(this);
        // SEARCH - Add the placeholder for Search and Turn this into in-line form control
        var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
        search_input.attr('placeholder', 'Search');
        search_input.addClass('form-control input-sm');
        // LENGTH - Inline-Form control
        var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
        length_sel.addClass('form-control input-sm');
    });
    // END DATATABLES
    




    // TABLESORTER
    $.extend($.tablesorter.themes.bootstrap, {
        // these classes are added to the table. To see other table classes available,
        // look here: http://twitter.github.com/bootstrap/base-css.html#tables
        table      : 'table',
        caption    : 'caption',
        header     : 'bootstrap-header', // give the header a gradient background
        footerRow  : '',
        footerCells: '',
        icons      : '', // add "icon-white" to make them white; this icon class is added to the <i> in the header
        sortNone   : 'fa fa-sort',
        sortAsc    : 'fa fa-sort-asc',     // includes classes for Bootstrap v2 & v3
        sortDesc   : 'fa fa-sort-desc', // includes classes for Bootstrap v2 & v3
        active     : '', // applied when column is sorted
        hover      : '', // use custom css here - bootstrap class may not override it
        filterRow  : '', // filter row class
        even       : '', // odd row zebra striping
        odd        : ''  // even row zebra striping
    });
    
    // **********************************
    //  Description of ALL pager options
    // **********************************
    var pagerOptions = {

        // target the pager markup - see the HTML block below
        container: $(".ts-pager"),

        // use this url format "http:/mydatabase.com?page={page}&size={size}&{sortList:col}"
        ajaxUrl: null,

        // modify the url after all processing has been applied
        customAjaxUrl: function(table, url) { return url; },

        // process ajax so that the data object is returned along with the total number of rows
        // example: { "data" : [{ "ID": 1, "Name": "Foo", "Last": "Bar" }], "total_rows" : 100 }
        ajaxProcessing: function(ajax){
          if (ajax && ajax.hasOwnProperty('data')) {
            // return [ "data", "total_rows" ];
            return [ ajax.total_rows, ajax.data ];
          }
        },

        // output string - default is '{page}/{totalPages}'
        // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
        output: '{startRow} to {endRow} ({totalRows})',

        // apply disabled classname to the pager arrows when the rows at either extreme is visible - default is true
        updateArrows: true,

        // starting page of the pager (zero based index)
        page: 0,

        // Number of visible rows - default is 10
        size: 10,

        // Save pager page & size if the storage script is loaded (requires $.tablesorter.storage in jquery.tablesorter.widgets.js)
        savePages : true,

        // if true, the table will remain the same height no matter how many records are displayed. The space is made up by an empty
        // table row set to a height to compensate; default is false
        fixedHeight: true,

        // remove rows from the table to speed up the sort of large tables.
        // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
        removeRows: false,

        // css class names of pager arrows
        cssNext: '.next', // next page arrow
        cssPrev: '.prev', // previous page arrow
        cssFirst: '.first', // go to first page arrow
        cssLast: '.last', // go to last page arrow
        cssGoto: '.gotoPage', // select dropdown to allow choosing a page

        cssPageDisplay: '.pagedisplay', // location of where the "output" is displayed
        cssPageSize: '.pagesize', // page size selector - select dropdown that sets the "size" option

        // class added to arrows when at the extremes (i.e. prev/first arrows are "disabled" when on the first page)
        cssDisabled: 'disabled', // Note there is no period "." in front of this class name
        cssErrorRow: 'tablesorter-errorRow' // ajax error information row
    };

    // Initialize tablesorter
    // ***********************
    $('.tablesorter').tablesorter({
        // this will apply the bootstrap theme if "uitheme" widget is included
        // the widgetOptions.uitheme is no longer required to be set
        theme : "bootstrap",

        widthFixed: true,

        headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!

        // widget code contained in the jquery.tablesorter.widgets.js file
        // use the zebra stripe widget if you plan on hiding any rows (filter widget)
        widgets : [ "uitheme", "filter" ],

        widgetOptions : {
          // reset filters button
          filter_reset : ".reset"

          // set the uitheme widget to use the bootstrap theme class names
          // this is no longer required, if theme is set
          // ,uitheme : "bootstrap"

        }
    })
    // initialize the pager plugin
    // ****************************
    .tablesorterPager(pagerOptions);
    // END TABLESORTER
    




    // SLIP JS (https://github.com/pornel/slip)
    $('[data-toggle=slippylist]').each(function(){
        var slippylist = this;

        slippylist.addEventListener('slip:beforereorder', function(e){
            if (/no-reorder/.test(e.target.className)) {
                e.preventDefault();
            }
        }, false);

        slippylist.addEventListener('slip:beforeswipe', function(e){
            if (e.target.nodeName == 'INPUT' || /no-swipe/.test(e.target.className)) {
                e.preventDefault();
            }
        }, false);

        slippylist.addEventListener('slip:beforewait', function(e){
            if (e.target.className.indexOf('slippylist-handle') > -1) e.preventDefault();
        }, false);

        slippylist.addEventListener('slip:afterswipe', function(e){
            e.target.parentNode.appendChild(e.target);
        }, false);

        slippylist.addEventListener('slip:reorder', function(e){
            e.target.parentNode.insertBefore(e.target, e.detail.insertBefore);
            return false;
        }, false);
        
        new Slip(slippylist);
    });
    // END SLIP JS
    



    // JQUERY MASK INPUT
    $('[data-mask="date"]').mask('00/00/0000');
    $('[data-mask="time"]').mask('00:00:00');
    $('[data-mask="date_time"]').mask('00/00/0000 00:00:00');
    $('[data-mask="zip"]').mask('00000-000');
    $('[data-mask="money"]').mask('000,000,000,000,000.00', {reverse: true});
    $('[data-mask="phone"]').mask('0000-0000');
    $('[data-mask="phone_with_ddd"]').mask('(00) 0000-0000');
    $('[data-mask="phone_us"]').mask('(000) 000-0000');
    $('[data-mask="cpf"]').mask('000.000.000-00', {reverse: true});
    $('[data-mask="ip_address"]').mask('099.099.099.099');
    $('[data-mask="percent"]').mask('##0,00%', {reverse: true});
    // END JQUERY MASK INPUT
    



    // TAGS INPUT
    $('[data-input="tags"], .input-tags').each(function(){
        var $this = $(this),
            height = ($this.attr('data-height') == undefined) ? 34 : $this.attr('data-height'),
            placeholder = ($this.attr('placeholder') == undefined) ? '' : $this.attr('placeholder');

        $this.tagsInput({
            'width': '100%',
            'height': 'auto',
            'defaultText': placeholder,
            'placeholderColor' : '#95a5a6'
        });
    });
    $('.tagsinput input').on('focus', function(){
        var input = $(this),
            tagsInput = input.parent().parent();

        tagsInput.addClass('focus');
    })
    .on('blur', function(){
        var input = $(this),
            tagsInput = input.parent().parent();

        tagsInput.removeClass('focus');
    });
    // END TAGS INPUT
    



    // MULTISELECT
    $('[data-input="multiselect"]').multiSelect();
    // END MULTISELECT
    

    

    // SELECT2
    $('[data-input="select2"], .select2').each(function(){
        var $this = $(this),
            placeholder = ($this.attr('placeholder') === undefined) ? 'Select a choice' : $this.attr('placeholder');

        $this.select2({
            placeholder: placeholder,
            allowClear: true
        });
    });
    $('[data-input="select2-tags"], .select2-tags').each(function(){
        var $this = $(this),
            placeholder = ($this.attr('placeholder') === undefined) ? 'Select a choice' : $this.attr('placeholder'),
            data_tags = ($this.attr('data-tags') === undefined) ? false : $this.attr('data-tags'),
            tags;

            if(data_tags){
                tags = data_tags.replace(/\s+/g, '');
                tags = tags.split(",");
            }
            else{
                tags = [];
            }

        $this.select2({
            placeholder: placeholder,
            tags: tags
        });
    });
    // END SELECT2
    



    // SELECTBOXIT
    $('[data-input="selectboxit"], .selectboxit').each(function(){
        var $this = $(this),
            placeholder = ($this.attr('placeholder') === undefined) ? 'Select a choice' : $this.attr('placeholder'),
            downArrowIcon = ($this.attr('data-arrow') === undefined) ? '' : $this.attr('data-arrow'),
            native = ($this.attr('data-native') === undefined) ? false : true;

        $this.selectBoxIt({
            defaultText: placeholder,
            downArrowIcon: downArrowIcon,
            native: native
        });
    });
    // END SELECTBOXIT
    



    // DATE RANGE PICKER
    $('[data-input="daterangepicker"]').each(function(){
        var $this = $(this),
            timePicker = ($this.attr('data-time') === undefined) ? false : true,
            format = ($this.attr('data-format') === undefined) ? 'MM/DD/YYYY' : $this.attr('data-format');

        $this.daterangepicker({
            timePicker: timePicker,
            timePickerIncrement: 5,
            format: format,
            applyClass: 'btn-primary'
        });
    });
    // END DATE RANGE PICKER
    



    // DATE PICKER
    $('[data-input="datepicker"]').each(function(){
        var $this = $(this),
            format = ($this.attr('data-format') === undefined) ? 'mm/dd/yyyy' : $this.attr('data-format'),
            startView = ($this.attr('data-view') === undefined) ? 0 : parseInt($this.attr('data-view'));

        $this.datepicker({
            format: format,
            startView: startView
        });
    });
    // END DATE PICKER
    




    // TIME PICKER
    $('[data-input="timepicker"]').timepicker({
        template: false
    }).on('changeTime.timepicker', function(e) {
        var $this = $(this),
            fake_input = $this.attr('data-fake-input');

        $(fake_input).val(e.time.value);
        $this.text(e.time.value);
    });;
    // END TIME PICKER
    




    // COLOR PICKER
    $('[data-input="colorpicker"]').each(function(){
        var $this = $(this);

        $this.minicolors({
            control: $(this).attr('data-control') || 'hue',
            defaultValue: $(this).attr('data-defaultValue') || '',
            inline: $(this).attr('data-inline') === 'true',
            letterCase: $(this).attr('data-letterCase') || 'lowercase',
            opacity: $(this).attr('data-opacity'),
            position: $(this).attr('data-position') || 'bottom left',
            theme: 'bootstrap'
        });
    });
    // END COLOR PICKER
     
     
    


    // FORM VALIDATE
    $('[data-validate="form"]').each(function(){
        var $this = $(this);
        
        $this.validate({
            focusCleanup: true,
            errorClass: "text-danger",
            errorPlacement: function(error, element) {
                if ( element.parent().hasClass('nice-checkbox') || element.parent().hasClass('nice-radio') || element.parent().hasClass('input-group') ) {
                    error.appendTo( element.parent().parent() );
                }
                else{
                    error.appendTo( element.parent() );
                }
            }
        });
    });
    // END FORM VALIDATE
    



    // DROPZONE
    $('form[data-input="dropzone"]').each(function(){
        var $this = $(this),
            url = $this.attr('action');

        $this.dropzone({ url: url });
    });
    // END DROPZONE
    




    // BOOTSTRAP WYSIHTML5
    $('[data-input="wysihtml5"]').each(function(i){
        var $this = $(this),
            font_styles = ($this.attr('data-fontstyles') === 'false') ? false : true,
            emphasis = ($this.attr('data-emphasis') === 'false') ? false : true,
            lists = ($this.attr('data-lists') === 'false') ? false : true,
            link = ($this.attr('data-link') === 'false') ? false : true,
            image = ($this.attr('data-image') === 'false') ? false : true,
            html = ($this.attr('data-html') === undefined) ? true : false,
            color = ($this.attr('data-color') === undefined) ? false : true;

        $this.wysihtml5({
            "font-styles": font_styles, //Font styling, e.g. h1, h2, etc. Default true
            "emphasis": emphasis, //Italics, bold, etc. Default true
            "lists": lists, //(Un)ordered lists, e.g. Bullets, Numbers. Default true
            "html": html, //Button which allows you to edit the generated HTML. Default false
            "link": link, //Button to insert a link. Default true
            "image": image, //Button to insert an image. Default true,
            "color": color, //Button to change color of font  
            "events": {
                "focus": function() { 
                    var editor = $this.data("wysihtml5").editor,
                        composer = editor.composer,
                        iframe = composer.iframe;

                    $(iframe).addClass('wysihtml5-focus');
                },
                "blur": function() { 
                    var editor = $this.data("wysihtml5").editor,
                        composer = editor.composer,
                        iframe = composer.iframe;

                    $(iframe).removeClass('wysihtml5-focus');
                }
            }
        });
    });
    // END BOOTSTRAP WYSIHTML5
    




    // MARKDOWN
    $('[data-input="markdown"]').markdown({
        autofocus:false,savable:false
    });
    // END MARKDOWN
    
    




    // easyPieChart
    $('.easyPieChart').each(function(){
        var $this = $(this),
            barColor = $this.attr('data-barColor'),
            trackColor = $this.attr('data-trackColor'),
            scaleColor = $this.attr('data-scaleColor'),
            lineWidth = $this.attr('data-lineWidth'),
            size = $this.attr('data-size'),
            rotate = $this.attr('data-rotate');

        // default for undefined
        barColor = (barColor === undefined) ? '#13A89E' : barColor ;        // teal
        trackColor = (trackColor === undefined) ? '#ecf0f1' : trackColor ;  // cloud
        scaleColor = (scaleColor === undefined) ? '#bdc3c7' : scaleColor ;  // silver
        lineWidth = (lineWidth === undefined) ? 3 : parseInt(lineWidth) ;
        size = (size === undefined) ? 110 : parseInt(size) ;
        rotate = (rotate === undefined) ? 0 : parseInt(rotate) ;

        trackColor = (trackColor == 'false' || trackColor == '') ? false : trackColor ;
        scaleColor = (scaleColor == 'false' || scaleColor == '') ? false : scaleColor ;

        // initilize easy pie chart
        $this.easyPieChart({
            barColor: barColor,
            trackColor: trackColor,
            scaleColor: scaleColor,
            lineWidth: lineWidth,
            size: size,
            rotate: rotate,
            onStep: function(from, to, currentValue) {
                $(this.el).find('span').text(currentValue.toFixed(0) +'%');
            }
        });
    });
});
    





// Be carefull to init wow: its not working on document ready
// WOW ANIMATED
// Panel Animated on viewport
var panel_aimated = true; // set to false if you dont want use animated on panel

if (panel_aimated) {
    var animated_panel = new WOW({
        boxClass:     'panel-animated',
        animateClass: 'animated fadeInUp',
        offset:       0
    });

    animated_panel.init();
};

// alias #1
var animated_onshow = new WOW({
    boxClass:     'animated-onshow',
    animateClass: 'animated',
    offset:       0
});

new WOW().init(); // use with default class .wow
// END WOW ANIMATED