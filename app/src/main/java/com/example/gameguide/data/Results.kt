/* 
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
package com.example.gameguide.data


data class Results (

	 val id : Int,
	 val slug : String,
	 val name : String,
	 val released : String,
	 val tba : Boolean,
	 val background_image : String,
	 val rating : Double,
	 val rating_top : Int,
	 val ratings : List<Ratings>,
	 val ratings_count : Int,
	 val reviews_text_count : Int,
	 val added : Int,
	 val added_by_status : Added_by_status,
	 val metacritic : Int,
	 val playtime : Int,
	 val suggestions_count : Int,
	 val updated : String,
	 val user_game : String,
	 val reviews_count : Int,
	 val saturated_color : String,
	 val dominant_color : String,
	 val platforms : List<Platforms>,
	 val parent_platforms : List<Parent_platforms>,
	 val genres : List<Genres>,
	 val clip : String,
	 val tags : List<Tags>,
	 val esrb_rating : Esrb_rating,
	 val short_screenshots : List<Short_screenshots>,
	 var visibility: Boolean = false

)