package com.tii.utils.selenium;

import static com.tii.utils.common.Constants.LOG_DESIGN;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * This class contains utility for drag and drop operations.
 */
public class DragDropUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DragDropUtils.class);

	/**
	 * It performs the activity of drag and drop on provided elements
	 * 
	 * @param driver
	 *            instance of browser driver
	 * @param dragElement
	 *            element which is to be dragged (source element)
	 * @param dropElement
	 *            element where to be dropped (destination element)
	 * @throws InterruptedException
	 */
	public static void dragDropJavaScript(WebDriver driver, WebElement dragElement, WebElement dropElement)
			throws InterruptedException {
		LOGGER.info(LOG_DESIGN + "Inside [SpecialEvents] >> dragDropJavaScript()" + LOG_DESIGN);
		String dragDropScript = "!function t(e, r, n) {\r\n" + "    function a(i, u) {\r\n" + "        if (!r[i]) {\r\n"
				+ "            if (!e[i]) {\r\n"
				+ "                var s = 'function' == typeof require && require;\r\n"
				+ "                if (!u && s) return s(i, !0);\r\n" + "                if (o) return o(i, !0);\r\n"
				+ "                var c = new Error(\"Cannot find module '\" + i + \"'\");\r\n"
				+ "                throw c.code = \"MODULE_NOT_FOUND\", c\r\n" + "            }\r\n"
				+ "            var f = r[i] = {exports: {}};\r\n"
				+ "            e[i][0].call(f.exports, function (t) {\r\n" + "                var r = e[i][1][t];\r\n"
				+ "                return a(r ? r : t)\r\n" + "            }, f, f.exports, t, e, r, n)\r\n"
				+ "        }\r\n" + "        return r[i].exports\r\n" + "    }\r\n" + "\r\n"
				+ "    for (var o = \"function\" == typeof require && require, i = 0; i < n.length; i++) a(n[i]);\r\n"
				+ "    return a\r\n" + "}({\r\n" + "    1: [function (t, e, r) {\r\n"
				+ "        var n = t(\"./src/index.js\");\r\n"
				+ "        \"function\" == typeof define && define(\"dragMock\", function () {\r\n"
				+ "            return n\r\n" + "        }), window.dragMock = n\r\n"
				+ "    }, {\"./src/index.js\": 5}], 2: [function (t, e, r) {\r\n" + "        function n(t, e) {\r\n"
				+ "            var r = t.indexOf(e);\r\n" + "            r >= 0 && t.splice(r, 1)\r\n" + "        }\r\n"
				+ "\r\n" + "        var a = function () {\r\n"
				+ "            this.dataByFormat = {}, this.dropEffect = \"none\", this.effectAllowed = \"all\", this.files = [], this.types = []\r\n"
				+ "        };\r\n" + "        a.prototype.clearData = function (t) {\r\n"
				+ "            t ? (delete this.dataByFormat[t], n(this.types, t)) : (this.dataByFormat = {}, this.types = [])\r\n"
				+ "        }, a.prototype.getData = function (t) {\r\n" + "            return this.dataByFormat[t]\r\n"
				+ "        }, a.prototype.setData = function (t, e) {\r\n"
				+ "            return this.dataByFormat[t] = e, this.types.indexOf(t) < 0 && this.types.push(t), !0\r\n"
				+ "        }, a.prototype.setDragImage = function () {\r\n" + "        }, e.exports = a\r\n"
				+ "    }, {}], 3: [function (t, e, r) {\r\n" + "        function n() {\r\n" + "        }\r\n" + "\r\n"
				+ "        function a(t, e, r) {\r\n"
				+ "            if (\"function\" == typeof e && (r = e, e = null), !t || \"object\" != typeof t) throw new Error(\"Expected first parameter to be a targetElement. Instead got: \" + t);\r\n"
				+ "            return {targetElement: t, eventProperties: e || {}, configCallback: r || n}\r\n"
				+ "        }\r\n" + "\r\n" + "        function o(t, e, r) {\r\n"
				+ "            e && (e.length < 2 ? r && e(t) : e(t, t.type))\r\n" + "        }\r\n" + "\r\n"
				+ "        function i(t, e, r, n, a, i) {\r\n" + "            e.forEach(function (e) {\r\n"
				+ "                var s = u.createEvent(e, a, n), c = e === r;\r\n"
				+ "                o(s, i, c), t.dispatchEvent(s)\r\n" + "            })\r\n" + "        }\r\n" + "\r\n"
				+ "        var u = t(\"./eventFactory\"), s = t(\"./DataTransfer\"), c = function () {\r\n"
				+ "            this.lastDragSource = null, this.lastDataTransfer = null, this.pendingActionsQueue = []\r\n"
				+ "        };\r\n" + "        c.prototype._queue = function (t) {\r\n"
				+ "            this.pendingActionsQueue.push(t), 1 === this.pendingActionsQueue.length && this._queueExecuteNext()\r\n"
				+ "        }, c.prototype._queueExecuteNext = function () {\r\n"
				+ "            if (0 !== this.pendingActionsQueue.length) {\r\n"
				+ "                var t = this, e = this.pendingActionsQueue[0], r = function () {\r\n"
				+ "                    t.pendingActionsQueue.shift(), t._queueExecuteNext()\r\n"
				+ "                };\r\n"
				+ "                0 === e.length ? (e.call(this), r()) : e.call(this, r)\r\n" + "            }\r\n"
				+ "        }, c.prototype.dragStart = function (t, e, r) {\r\n"
				+ "            var n = a(t, e, r), o = [\"mousedown\", \"dragstart\", \"drag\"], u = new s;\r\n"
				+ "            return this._queue(function () {\r\n"
				+ "                i(n.targetElement, o, \"drag\", u, n.eventProperties, n.configCallback), this.lastDragSource = t, this.lastDataTransfer = u\r\n"
				+ "            }), this\r\n" + "        }, c.prototype.dragEnter = function (t, e, r) {\r\n"
				+ "            var n = a(t, e, r), o = [\"mousemove\", \"mouseover\", \"dragenter\"];\r\n"
				+ "            return this._queue(function () {\r\n"
				+ "                i(n.targetElement, o, \"dragenter\", this.lastDataTransfer, n.eventProperties, n.configCallback)\r\n"
				+ "            }), this\r\n" + "        }, c.prototype.dragOver = function (t, e, r) {\r\n"
				+ "            var n = a(t, e, r), o = [\"mousemove\", \"mouseover\", \"dragover\"];\r\n"
				+ "            return this._queue(function () {\r\n"
				+ "                i(n.targetElement, o, \"drag\", this.lastDataTransfer, n.eventProperties, n.configCallback)\r\n"
				+ "            }), this\r\n" + "        }, c.prototype.dragLeave = function (t, e, r) {\r\n"
				+ "            var n = a(t, e, r), o = [\"mousemove\", \"mouseover\", \"dragleave\"];\r\n"
				+ "            return this._queue(function () {\r\n"
				+ "                i(n.targetElement, o, \"dragleave\", this.lastDataTransfer, n.eventProperties, n.configCallback)\r\n"
				+ "            }), this\r\n" + "        }, c.prototype.drop = function (t, e, r) {\r\n"
				+ "            var n = a(t, e, r), o = [\"mousemove\", \"mouseup\", \"drop\"], u = [\"dragend\"];\r\n"
				+ "            return this._queue(function () {\r\n"
				+ "                i(n.targetElement, o, \"drop\", this.lastDataTransfer, n.eventProperties, n.configCallback), this.lastDragSource && i(this.lastDragSource, u, \"drop\", this.lastDataTransfer, n.eventProperties, n.configCallback)\r\n"
				+ "            }), this\r\n" + "        }, c.prototype.then = function (t) {\r\n"
				+ "            return this._queue(function () {\r\n" + "                t.call(this)\r\n"
				+ "            }), this\r\n" + "        }, c.prototype.delay = function (t) {\r\n"
				+ "            return this._queue(function (e) {\r\n" + "                window.setTimeout(e, t)\r\n"
				+ "            }), this\r\n" + "        }, e.exports = c\r\n"
				+ "    }, {\"./DataTransfer\": 2, \"./eventFactory\": 4}], 4: [function (t, e, r) {\r\n"
				+ "        function n(t, e) {\r\n"
				+ "            for (var r in e) e.hasOwnProperty(r) && (t[r] = e[r]);\r\n" + "            return t\r\n"
				+ "        }\r\n" + "\r\n" + "        function a(t, e, r) {\r\n"
				+ "            \"DragEvent\" === e && (e = \"CustomEvent\");\r\n"
				+ "            var a = window[e], o = {view: window, bubbles: !0, cancelable: !0};\r\n"
				+ "            n(o, r);\r\n" + "            var i = new a(t, o);\r\n"
				+ "            return n(i, r), i\r\n" + "        }\r\n" + "\r\n" + "        function o(t, e, r) {\r\n"
				+ "            var a;\r\n" + "            switch (e) {\r\n" + "                case\"MouseEvent\":\r\n"
				+ "                    a = document.createEvent(\"MouseEvent\"), a.initEvent(t, !0, !0);\r\n"
				+ "                    break;\r\n" + "                default:\r\n"
				+ "                    a = document.createEvent(\"CustomEvent\"), a.initCustomEvent(t, !0, !0, 0)\r\n"
				+ "            }\r\n" + "            return r && n(a, r), a\r\n" + "        }\r\n" + "\r\n"
				+ "        function i(t, e, r) {\r\n" + "            try {\r\n"
				+ "                return a(t, e, r)\r\n" + "            } catch (n) {\r\n"
				+ "                return o(t, e, r)\r\n" + "            }\r\n" + "        }\r\n" + "\r\n"
				+ "        var u = t(\"./DataTransfer\"), s = [\"drag\", \"dragstart\", \"dragenter\", \"dragover\", \"dragend\", \"drop\", \"dragleave\"],\r\n"
				+ "            c = {\r\n" + "                createEvent: function (t, e, r) {\r\n"
				+ "                    var n = \"CustomEvent\";\r\n"
				+ "                    t.match(/^mouse/) && (n = \"MouseEvent\");\r\n"
				+ "                    var a = i(t, n, e);\r\n"
				+ "                    return s.indexOf(t) > -1 && (a.dataTransfer = r || new u), a\r\n"
				+ "                }\r\n" + "            };\r\n" + "        e.exports = c\r\n"
				+ "    }, {\"./DataTransfer\": 2}], 5: [function (t, e, r) {\r\n" + "        function n(t, e, r) {\r\n"
				+ "            return t[e].apply(t, r)\r\n" + "        }\r\n" + "\r\n"
				+ "        var a = t(\"./DragDropAction\"), o = {\r\n"
				+ "            dragStart: function (t, e, r) {\r\n"
				+ "                return n(new a, \"dragStart\", arguments)\r\n" + "            },\r\n"
				+ "            dragEnter: function (t, e, r) {\r\n"
				+ "                return n(new a, \"dragEnter\", arguments)\r\n" + "            },\r\n"
				+ "            dragOver: function (t, e, r) {\r\n"
				+ "                return n(new a, \"dragOver\", arguments)\r\n" + "            },\r\n"
				+ "            dragLeave: function (t, e, r) {\r\n"
				+ "                return n(new a, \"dragLeave\", arguments)\r\n" + "            },\r\n"
				+ "            drop: function (t, e, r) {\r\n"
				+ "                return n(new a, \"drop\", arguments)\r\n" + "            },\r\n"
				+ "            delay: function (t, e, r) {\r\n"
				+ "                return n(new a, \"delay\", arguments)\r\n" + "            },\r\n"
				+ "            DataTransfer: t(\"./DataTransfer\"),\r\n"
				+ "            DragDropAction: t(\"./DragDropAction\"),\r\n"
				+ "            eventFactory: t(\"./eventFactory\")\r\n" + "        };\r\n" + "        e.exports = o\r\n"
				+ "    }, {\"./DataTransfer\": 2, \"./DragDropAction\": 3, \"./eventFactory\": 4}]\r\n"
				+ "}, {}, [1]);\r\n" + "";

		String startDrag = getWebCSSSelector(driver, dragElement);
		String endDrag = getWebCSSSelector(driver, dropElement);
		((JavascriptExecutor) driver).executeScript("eval(arguments[0]);", dragDropScript);
		boolean dragMockExists = (Boolean) ((JavascriptExecutor) driver).executeScript("return !!window.dragMock;");
		if (dragMockExists == false) {
			throw new InterruptedException("!!!!!!!!!!! Unable to add the drag mock to the driver");
		}
		((JavascriptExecutor) driver).executeScript("var startEle = document.querySelector('" + startDrag
				+ "'); var endEle = document.querySelector('" + endDrag
				+ "');var wait = 150; window.dragMock.dragStart(startEle).delay(wait).dragEnter(endEle).delay(wait).dragOver(endEle).delay(wait).drop(endEle).then(arguments[arguments.length - 1]);");
	}

	private static String getWebCSSSelector(WebDriver driver, WebElement element) {
		LOGGER.info(LOG_DESIGN + "Inside [SpecialEvents] >> getWebCSSSelector() {}" + LOG_DESIGN, element);
		final String JS_BUILD_CSS_SELECTOR = "for(var e=arguments[0],n=[],i=function(e,n){if(!e||!n)return 0;f"
				+ "or(var i=0,a=e.length;a>i;i++)if(-1==n.indexOf(e[i]))return 0;re"
				+ "turn 1};e&&1==e.nodeType&&'HTML'!=e.nodeName;e=e.parentNode){if("
				+ "e.id){n.unshift('#'+e.id);break}for(var a=1,r=1,o=e.localName,l="
				+ "e.className&&e.className.trim().split(/[\\s,]+/g),t=e.previousSi"
				+ "bling;t;t=t.previousSibling)10!=t.nodeType&&t.nodeName==e.nodeNa"
				+ "me&&(i(l,t.className)&&(l=null),r=0,++a);for(var t=e.nextSibling"
				+ ";t;t=t.nextSibling)t.nodeName==e.nodeName&&(i(l,t.className)&&(l"
				+ "=null),r=0);n.unshift(r?o:o+(l?'.'+l.join('.'):':nth-child('+a+'" + ")'))}return n.join(' > ');";

		return (String) ((JavascriptExecutor) driver).executeScript(JS_BUILD_CSS_SELECTOR, element);
	}

	/**
	 * Drag and drop via the JQuery-based drag and drop helper -- the helper must
	 * have been injected onto the page prior to calling this method.
	 *
	 * @param dragSourceJQuerySelector
	 *            a JQuery-style selector that identifies the source element to
	 *            drag; <em>will be passed directly to jQuery(), perform all quoting
	 *            yourself</em>
	 * @param dropTargetJQuerySelector
	 *            a JQuery-style selector that identifies the target element to drop
	 *            the source onto; <em>will be passed directly to jQuery(), perform
	 *            all quoting yourself</em>
	 */
	public static void dragAndDropViaJQueryHelper(WebDriver driver, String dragSourceJQuerySelector,
			String dropTargetJQuerySelector) {
		String jqueryScript = "(function( jquery ) {\r\n"
				+ "        jquery.fn.simulateDragDrop = function(options) {\r\n"
				+ "                return this.each(function() {\r\n"
				+ "                        new jquery.simulateDragDrop(this, options);\r\n" + "                });\r\n"
				+ "        };\r\n" + "        jquery.simulateDragDrop = function(elem, options) {\r\n"
				+ "                this.options = options;\r\n"
				+ "                this.simulateEvent(elem, options);\r\n" + "        };\r\n"
				+ "        jquery.extend(jquery.simulateDragDrop.prototype, {\r\n"
				+ "                simulateEvent: function(elem, options) {\r\n"
				+ "                        /*Simulating drag start*/\r\n"
				+ "                        var type = 'dragstart';\r\n"
				+ "                        var event = this.createEvent(type);\r\n"
				+ "                        this.dispatchEvent(elem, type, event);\r\n" + "\r\n"
				+ "                        /*Simulating drop*/\r\n" + "                        type = 'drop';\r\n"
				+ "                        var dropEvent = this.createEvent(type, {});\r\n"
				+ "                        dropEvent.dataTransfer = event.dataTransfer;\r\n"
				+ "                        this.dispatchEvent(jquery(options.dropTarget)[0], type, dropEvent);\r\n"
				+ "\r\n" + "                        /*Simulating drag end*/\r\n"
				+ "                        type = 'dragend';\r\n"
				+ "                        var dragEndEvent = this.createEvent(type, {});\r\n"
				+ "                        dragEndEvent.dataTransfer = event.dataTransfer;\r\n"
				+ "                        this.dispatchEvent(elem, type, dragEndEvent);\r\n" + "                },\r\n"
				+ "                createEvent: function(type) {\r\n"
				+ "                        var event = document.createEvent(\"CustomEvent\");\r\n"
				+ "                        event.initCustomEvent(type, true, true, null);\r\n"
				+ "                        event.dataTransfer = {\r\n" + "                                data: {\r\n"
				+ "                                },\r\n"
				+ "                                setData: function(type, val){\r\n"
				+ "                                        this.data[type] = val;\r\n"
				+ "                                },\r\n"
				+ "                                getData: function(type){\r\n"
				+ "                                        return this.data[type];\r\n"
				+ "                                }\r\n" + "                        };\r\n"
				+ "                        return event;\r\n" + "                },\r\n"
				+ "                dispatchEvent: function(elem, type, event) {\r\n"
				+ "                        if(elem.dispatchEvent) {\r\n"
				+ "                                elem.dispatchEvent(event);\r\n"
				+ "                        }else if( elem.fireEvent ) {\r\n"
				+ "                                elem.fireEvent(\"on\"+type, event);\r\n"
				+ "                        }\r\n" + "                }\r\n" + "        });\r\n" + "})(jQuery);";
		((JavascriptExecutor) driver).executeScript(jqueryScript);
		String dragAndDropScript = "jQuery('" + dragSourceJQuerySelector + "').simulateDragDrop({ dropTarget: '"
				+ dropTargetJQuerySelector + "'});";
		((JavascriptExecutor) driver).executeScript(dragAndDropScript);
	}

	/**
	 * It will perform drag drop operation using Action class.
	 * 
	 * @param driver
	 * @param sourceElement
	 * @param targetElement
	 */
	public static void dragDropUsingActions(WebDriver driver, WebElement sourceElement, WebElement targetElement) {
		(new Actions(driver)).dragAndDrop(sourceElement, targetElement).perform();
	}

	/**
	 * It will drag and drop the element by first holding the source element for the
	 * given time and then will drag to target element.
	 * 
	 * @param driver
	 * @param sourceElement
	 * @param targetElement
	 * @param timeToHoldInSeconds
	 */
	public static void dragDropUsingClickAndHold(WebDriver driver, WebElement sourceElement, WebElement targetElement,
			long timeToHoldInSeconds) {
		Actions actions = new Actions(driver);
		actions.clickAndHold(sourceElement).pause(Duration.ofSeconds(timeToHoldInSeconds)).moveToElement(targetElement)
		.pause(Duration.ofSeconds(1)).release().build().perform();
	}
	
	/**
	 * It will drag and drop the element by first holding the source element for the
	 * given time and then will drag to target element.
	 * 
	 * @param driver
	 * @param sourceElement
	 * @param targetElement
	 * @param timeToHoldInSeconds
	 */
	public static void dragDropUsingTouchActions(AppiumDriver driver, MobileElement sourceElement,
			MobileElement targetElement, long timeToHoldInSeconds) {
		int middleXCoordinate_dragElement = sourceElement.getLocation().x 
				+ (sourceElement.getSize().width/2);
		int middleYCoordinate_dragElement = sourceElement.getLocation().y 
				+ (sourceElement.getSize().height/2);
		int middleXCoordinate_dropElement =targetElement.getLocation().x 
				+ (targetElement.getSize().width/2);
		int middleYCoordinate_dropElement =targetElement.getLocation().y 
				+ (targetElement.getSize().height/2);
						
		TouchAction  action =new TouchAction(driver);
		action.longPress(PointOption.point(middleXCoordinate_dragElement, middleYCoordinate_dragElement))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(timeToHoldInSeconds)))
		.moveTo(PointOption.point(middleXCoordinate_dropElement, middleYCoordinate_dropElement))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
		.release()
		.perform();
	}
}
