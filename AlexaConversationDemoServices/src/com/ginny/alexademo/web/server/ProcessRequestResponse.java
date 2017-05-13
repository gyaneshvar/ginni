package com.ginny.alexademo.web.server;


public class ProcessRequestResponse {

	// String getSampleResponse(Map<String, Object> request) {
	// String response =
	// "{\"response\": { \"outputSpeech\": {	\"type\": \"PlainText\",\"text\": \"OK, I will now say hello world\"}";
	//
	// }
	/*
	 * https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/alexa-skills-kit-interface-reference#response-format
	 * 
	 -----------------request-----------------------------------
	 {
	"version": "string",
	"session": {
	"new": true,
	"sessionId": "string",
	"application": {
	  "applicationId": "string"
	},
	"attributes": {
	  "string": {}
	},
	"user": {
	  "userId": "string",
	  "accessToken": "string"
	}
	},
	"context": {
	"System": {
	  "application": {
	    "applicationId": "string"
	  },
	  "user": {
	    "userId": "string",
	    "accessToken": "string"
	  },
	  "device": {
	    "supportedInterfaces": {
	      "AudioPlayer": {}
	    }
	  }
	},
	"AudioPlayer": {
	  "token": "string",
	  "offsetInMilliseconds": 0,
	  "playerActivity": "string"
	}
	},
	"request": {}
	}

	  ----------------response-------------------------------
	  {
	  "version": "string",
	  "sessionAttributes": {
	    "string": object
	  },
	  "response": {
	    "outputSpeech": {
	      "type": "string",
	      "text": "string",
	      "ssml": "string"
	    },
	    "card": {
	      "type": "string",
	      "title": "string",
	      "content": "string",
	      "text": "string",
	      "image": {
	        "smallImageUrl": "string",
	        "largeImageUrl": "string"
	      }
	    },
	    "reprompt": {
	      "outputSpeech": {
	        "type": "string",
	        "text": "string",
	        "ssml": "string"
	      }
	    },
	    "directives": [
	      {
	        "type": "string",
	        "playBehavior": "string",
	        "audioItem": {
	          "stream": {
	            "token": "string",
	            "url": "string",
	            "offsetInMilliseconds": 0
	          }
	        }
	      }
	    ],
	    "shouldEndSession": boolean
	  }
	}

	-----------------------------response sub tag contnets------------------------------------
						 * "response": {
	"outputSpeech": {
	"type": "PlainText",
	"text": "OK, I will now say hello world"
	},
	 */
}
