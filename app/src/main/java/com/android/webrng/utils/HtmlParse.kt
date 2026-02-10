package com.android.webrng.utils

import android.R
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.android.webrng.constants.maxNameLength


fun sendHeadsUpNotification(context: Context, title: String, message: String) {
    val builder: Notification.Builder? = Notification.Builder(context)
        .setSmallIcon(R.drawable.star_big_on)
        .setContentTitle(title)
        .setContentText(message)
        .setDefaults(Notification.DEFAULT_ALL)
        .setPriority(Notification.PRIORITY_HIGH)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (builder != null)
        notificationManager.notify(0, builder.build())
}

class HtmlParse {
    companion object {
        fun getScript(input: String, search: String): String {
            var position = input.indexOf("$search")
        //    Log.i("POSITION", "$position")
            if (position >= 0 && position < input.length) {
                var count: Int = 0
                var s = position
                while (s < input.length) {
                    if (input[s] == '[')
                        break
                    s++
                }
                position = s

                while (s < input.length) {
                    if (input[s] == '[') {
                        count++
                    }
                    if (input[s] == ']') {
                        count--
                    }
                    if (count <= 0)
                        break
                    s++
                }

                if (position >= 0 && position < input.length && s+1 > position && s < input.length) {
                    return input.substring(position, s+1)
                }
            }
            return ""
        }
        fun getParameter(input: String, search: String): String {
        //    Log.i("SEARCH", "$input")
            var position = 0
            if (position < input.length) {
                var s = position
                while (s < input.length) {
                    if (input[s] == '>')
                        break
                    s++
                }

                var header: String = ""
                if (s+1 > position && s < input.length) {
                    header = input.substring(position, s+1)
                }

                if (header.length > search.length+4) {
                    position = header.indexOf("$search")
                    if (position >= 1 && position < header.length) {
                        var start: Int = -1
                        var init: Boolean = false
                        s = position
                        if (s <= 0)
                            return ""

                        while (s < header.length) {
                            if (input[s] == '\"' && input[s-1] != '\\') {
                                if (!init) {
                                    start = s+1
                                    init = true
                                }
                                else {
                                    break
                                }
                            }
                            s++
                        }

                        if (start >= 0 && start < input.length && s > start && s <= input.length) {
                            return input.substring(start, s)
                        }
                    }
                }
            }
            return ""
        }
        fun getById(input: String, tag: String, search: String): String {
            val position = input.indexOf("id=\"$search\"")
        //    Log.i("POSITION", "$position")
            if (position >= tag.length && position >= 0 && position < input.length) {
                var count: Int = 1
                var s = position
                while (s < input.length) {
                    if (input[s] == '<') {
                        val taskStr = input.substring(s, (s+tag.length).coerceAtMost(input.length))
                    //    Log.i("<", "$taskStr")
                        if (taskStr == tag)
                            count++
                    }
                    if (input[s] == '>') {
                        val taskStr = input.substring(s-tag.length, s)
                    //    Log.i(">", "$taskStr")
                        if (taskStr == tag)
                            count--
                    }
                    if (count <= 0)
                        break
                    s++
                }

                val index1 = position-tag.length-2
                if (position >= tag.length+2 && index1 < input.length && s+1 > index1 && s < input.length) {
                    return input.substring(index1, s+1)
                }
            }
            return ""
        }
        fun getByTag(input: String, tag: String): Array<String> {
            val outputList: ArrayList<String> = ArrayList<String>()
            var position = input.indexOf("<$tag")
            while (position >= 0 && position < input.length) {
            //    Log.i("POSITION", "$position")
                if (position >= tag.length && position >= 0 && position < input.length) {
                    var count: Int = 0
                    var s = position
                    while (s < input.length - 1) {
                        if (input[s] == '<') {
                            val taskStr = input.substring(s+1, (s+tag.length+1).coerceAtMost(input.length))
                            //    Log.i("<", "$taskStr")
                            if (taskStr == tag)
                                count++
                        }
                        if (input[s] == '>') {
                            val taskStr = input.substring(s-tag.length, s)
                            //    Log.i(">", "$taskStr")
                            if (taskStr == tag)
                                count--
                        }
                        if (count <= 0)
                            break
                        s++
                    }

                    if (position >= 0 && position < input.length && s+1 > position && s < input.length) {
                        outputList.add(input.substring(position, s+1))
                    //    Log.i("LIST", outputList[outputList.size - 1])
                    }
                }
                position = input.indexOf("<$tag", position+1)
            }
            return outputList.toTypedArray()
        }
        fun getValue(input: String): String {
            val position = input.indexOf(">")
            if (position >= 0 && position < input.length) {
                var s = position
                while (s < input.length) {
                    if (input[s] == '<')
                        break
                    s++
                }

                if (position >= 0 && position < input.length-1 && s > position+1 && s <= input.length) {
                    return input.substring(position+1, s)
                    //    Log.i("LIST", outputList[outputList.size - 1])
                }
            }
            return ""
        }
        fun compressString(input: String): String {
            if (input.length <= maxNameLength) {
                return input
            }
            else {
                val totalCount = input.length - maxNameLength
                var taskCount = 0
                val lettersList = arrayOf('a', 'e', 'i', 'o', 'u', 'y', 'а', 'е', 'и', 'о', 'у', 'э', 'ю', 'я')
                var outputResult: String = ""

                var s = input.length - 1
                var wordStart: Boolean = false
                while(s > 0 && taskCount < totalCount) {
                    if (input[s] != ' ') {
                        if (!wordStart) {
                            wordStart = true
                            outputResult += input[s--]
                            continue
                        }

                        if (input[s] !in lettersList || input[s-1] == ' ')
                            outputResult += input[s]
                        else
                            taskCount++
                    }
                    else {
                        wordStart = false
                        outputResult += input[s]
                    }
                    s--
                }

                while (s >= 0) {
                    outputResult += input[s]
                    s--
                }
                outputResult = outputResult.reversed()
                return outputResult
            }
        }
    }
}