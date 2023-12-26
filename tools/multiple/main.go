package main

import (
	"fmt"

	"github.com/miekg/dns"
)

const (
	SERVER_ADDR    = "127.0.0.1:2053"
)

func domainPairForCompression() (string, string) {
	return "abc.longassdomainname.com.", "def.longassdomainname.com."
}

func sendMultiQuery(request *dns.Msg) (*dns.Msg, error) {
	c := new(dns.Client)
	fmt.Printf("Querying the following in the same request (Messages with >> prefix are part of this log)\n")
	for _, question := range request.Question {
		fmt.Printf(">> %s\n", question.String())
	}

	fmt.Printf("Sending Request")

	response, _, err := c.Exchange(request, SERVER_ADDR)
	if err != nil {
		return nil, fmt.Errorf("DNS query failed: %s.\nIf you are seeing this after a while then it is likely that your server is not responding with appropriate id\n", err)
	}
	if !response.MsgHdr.Response {
		return nil, fmt.Errorf("Expected QR field to be set to 1. 1 indicates that it is a response. Got 0\n")
	}
	if response.MsgHdr.Authoritative {
		return nil, fmt.Errorf("Expected AA field to not be set. Got 1. 1 indicates that the response is authoritative which is not true for this server.\n")
	}

	fmt.Printf("Received Response\n")

	return response, nil
}

func main() {
	queryDomain1, queryDomain2 := domainPairForCompression()
	
	request := new(dns.Msg)
	request.SetQuestion(dns.Fqdn(queryDomain1), dns.TypeA)
	request.Question = append(request.Question, dns.Question{Name: queryDomain2, Qtype: dns.TypeA, Qclass: dns.ClassINET})
	request.Compress = true

	response, err := sendMultiQuery(request)

	if err != nil {
		return
	}

	if len(response.Answer) != 2 {
		fmt.Printf("Expected answer section to have 2 entries got %d\n", len(response.Answer))
	}
}
